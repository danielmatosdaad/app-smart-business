package br.com.app.smart.business.builder.infra;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Logger;
import br.com.app.smart.business.exception.InfraEstruturaException;

@Named
public class FabricaGenericaDados {

	@Inject
	private static Logger log;

	public static <T> T criarInstancia(Class<T> clazz, Object... objetos) throws InfraEstruturaException {

		try {

			List<Class<T>> classesParametros = new ArrayList<Class<T>>();

			for (Object object : objetos) {

				classesParametros.add((Class<T>) object.getClass());
			}

			Class[] arrayClass = new Class[classesParametros.size()];
			classesParametros.toArray(arrayClass);
			System.out.println(arrayClass.toString());
			Constructor<T> construtor = clazz.getDeclaredConstructor(arrayClass);
			T instancia = construtor.newInstance(objetos);
			return instancia;

		} catch (ClassCastException cce) {

			log.log(java.util.logging.Level.SEVERE, "", cce);
			throw new InfraEstruturaException(cce);
		} catch (Exception e) {
			log.log(java.util.logging.Level.SEVERE, "", e);
			throw new InfraEstruturaException(e);
		}

	}

	public static <T> T criarInstancia(Class<T> clazz) throws InfraEstruturaException {
		try {
			T instancia = (T) clazz.newInstance();
			return instancia;
		} catch (Exception e) {
			log.log(java.util.logging.Level.SEVERE, "", e);
			throw new InfraEstruturaException(e);
		}
	}

	public static <T> List<T> createListOfType(Class<T> type) {
		return new ArrayList<T>();
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> transferirListaDados(Class<T> clazzQueReceberaDados, List lista)
			throws InfraEstruturaException {

		List<T> listaResultado = new ArrayList<T>();

		for (Object object : lista) {

			listaResultado.add(trataDados(clazzQueReceberaDados, object));
		}
		return listaResultado;
	}

	@SuppressWarnings("unchecked")
	public static <T> T transferirDados(Class<T> clazzQueReceberaDados, Object dadosParaTransferir)
			throws InfraEstruturaException {

		return trataDados(clazzQueReceberaDados, dadosParaTransferir);
	
	}

	private static <T> T trataDados(Class<T> clazzQueReceberaDados, Object dadosParaTransferir)
			throws InfraEstruturaException {
		try {
			
			if (dadosParaTransferir == null) {
				return null;
			}

			T instancia = criarInstancia(clazzQueReceberaDados);

			if (instancia == null) {

				return null;
			}

			for (Field field : dadosParaTransferir.getClass().getDeclaredFields()) {
				field.setAccessible(true);

				if (java.lang.reflect.Modifier.isStatic(field.getModifiers())
						|| java.lang.reflect.Modifier.isFinal(field.getModifiers())) {
					continue;
				}
				Object value = field.get(dadosParaTransferir);
				if (value == null) {
					continue;
				}

				if (field.getType().isEnum()) {

					final Enum<?> theOneAndOnly = tratarEnum(instancia, field, value);
					value = theOneAndOnly;
				}

				if (List.class.isAssignableFrom(field.getType())) {

					if (value instanceof List) {
						List lista = (List) value;

						if (lista.size() == 0) {
							System.out.println("lista vazia");
							continue;

						}
					}
					Object lista = tratarLista(instancia, field, value);
					setDado(instancia, field.getName(), lista, instancia.getClass());
					continue;

				}

				if (value != null) {
					System.out.println(field.getName() + "=" + value);
					set(instancia, field.getName(), value);
				}
			}
			return instancia;
		} catch (Exception e) {
			e.printStackTrace();
			log.log(java.util.logging.Level.SEVERE, "", e);
			throw new InfraEstruturaException(e);
		}
	}

	private static List tratarLista(Object instancia, Field field, Object value) throws NoSuchFieldException,
			NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		System.out.println("O atributo eh uma lista");
		Field fieldInstance = instancia.getClass().getDeclaredField(field.getName());
		ParameterizedType genericType = (ParameterizedType) fieldInstance.getGenericType();
		Class<?> parametroLista = (Class<?>) genericType.getActualTypeArguments()[0];
		System.out.println("Tipo da lista: " + parametroLista);

		List listQueReceberaDados = createListOfType(parametroLista);
		List listAserTransferida = (List) value;
		System.out.println("tamanho da lista a ser transferida:" + listAserTransferida.size());
		for (Object object : listAserTransferida) {

			try {
				Method add = List.class.getDeclaredMethod("add", Object.class);
				Object dado = trataDados(parametroLista, object);
				add.invoke(listQueReceberaDados, dado);
			} catch (InfraEstruturaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return listQueReceberaDados;
	}

	private static Enum<?> tratarEnum(Object instancia, Field field, Object value) throws InfraEstruturaException {

		String nomeAtributo = field.getName();
		String nameEnum = value.toString();
		Field enumFild = null;
		Class<? extends Enum> enumType = null;
		Enum<?> theOneAndOnly = null;

		try {
			enumFild = instancia.getClass().getDeclaredField(nomeAtributo);
			enumType = (Class<? extends Enum>) enumFild.getType();
			theOneAndOnly = Enum.valueOf(enumType, nameEnum);
		} catch (Exception e) {
			e.printStackTrace();
			log.log(java.util.logging.Level.SEVERE, "", e);
			throw new InfraEstruturaException(e);

		}
		return theOneAndOnly;
	}

	public static boolean set(Object object, String fieldName, Object fieldValue) {
		Class<?> clazz = object.getClass();
		try {

			Inicio: {// verificar os tipos
				// System.out.println("--------------------------");
				// System.out.println(object.getClass().getName());
				// System.out.println(fieldName);
				// System.out.println(fieldValue.getClass().getName());
				if (isTipoIguais(object, fieldName, fieldValue)) {

					setDado(object, fieldName, fieldValue, clazz);
					return true;
				} else {

					Object obj = transferirDados(object.getClass().getDeclaredField(fieldName).getType(), fieldValue);
					setDado(object, fieldName, obj, clazz);

				}
			}
		} catch (NoSuchFieldException e) {
			clazz = clazz.getSuperclass();

		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}
		return false;
	}

	public static boolean verificaPrimitivo(Type tipo) {

		if (tipo == char.class) {
			return true;
		} else if (tipo == byte.class) {
			return true;
		} else if (tipo == short.class) {
			return true;
		} else if (tipo == int.class) {
			return true;
		} else if (tipo == long.class) {
			return true;
		} else if (tipo == float.class) {
			return true;
		}

		return false;
	}

	private static boolean isPrimitivosIguais(Object object, String fieldName, Object fieldValue)
			throws NoSuchFieldException {

		try {
			if (object.getClass().getDeclaredField(fieldName).getType().isPrimitive()) {

				return true;
			}
		} catch (Exception e) {
			// bloco seguro, pois posso estar testando um privimito como fosse
			// objeto
		}

		try {
			if (fieldValue.getClass().getDeclaredField(fieldName).getType().isPrimitive()) {

				return true;
			}
		} catch (Exception e) {
			// bloco seguro, pois posso estar testando um privimito como fosse
			// objeto
		}

		if (fieldValue instanceof Number) {

			return true;
		}
		if (fieldValue instanceof Character) {

			return true;
		}

		if (fieldValue instanceof Byte) {

			return true;
		}
		return false;
	}

	private static boolean isTipoIguais(Object object, String fieldName, Object fieldValue)
			throws NoSuchFieldException {

		if (isPrimitivosIguais(object, fieldName, fieldValue)) {
			// System.out.println("PRIMITIVO");
			return true;
		}
		// System.out.println("NAO PRIMITIVO");
		return object.getClass().getDeclaredField(fieldName).getType().getName()
				.equals(fieldValue.getClass().getName());
	}

	private static void setDado(Object object, String fieldName, Object fieldValue, Class<?> clazz)
			throws NoSuchFieldException, IllegalAccessException {
		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(object, fieldValue);
	}

	@SuppressWarnings("unchecked")
	public static <V> V get(Object object, String fieldName) {
		Class<?> clazz = object.getClass();
		while (clazz != null) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				return (V) field.get(object);
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
		return null;
	}

//	public static void main(String args[]) throws InfraEstruturaException {
//
//		// testParametro();
//		// testUsuario();
//		// testeMetadado();
//		// testeTela();
//		testeFuncionalidadeDTO();
//
//	}
//
//	private static void testeFuncionalidadeDTO() throws InfraEstruturaException {
//
//		Funcionalidade e1 = new Funcionalidade();
//		e1.setId(1L);
//		e1.setNomeFuncionalidade("Manter Usuario");
//		e1.setDescricao("Possibilita realizar alguma coisa1");
//
//		List<Funcionalidade> lista2 = new ArrayList<Funcionalidade>();
//		lista2.add(e1);
//		GrupoFuncionalidade g1 = new GrupoFuncionalidade();
//		g1.setId(1L);
//		g1.setNomeGrupoFuncionalidade("Usuario2");
//		g1.setDescricao("Possibilita realizar alguma coisa2");
//		g1.setFuncionalidades(lista2);
//
//		e1.setGrupoFuncionalidade(g1);
//		List<Funcionalidade> lista = new ArrayList<Funcionalidade>();
//		lista.add(e1);
//		List<FuncionalidadeDTO> listaDTO = new ArrayList<FuncionalidadeDTO>();
//
//		for (Funcionalidade Funcionalidade : lista) {
//			FuncionalidadeDTO dto = FabricaGenericaDados.transferirDados(FuncionalidadeDTO.class, Funcionalidade);
//			listaDTO.add(dto);
//		}
//
//		System.out.println(listaDTO);
//	}
//
//	private static void testeTela() throws InfraEstruturaException {
//
//		TelaDTO dto = new TelaDTO();
//		dto.setDescricaoTela("descricaoTela Tela");
//		dto.setNomeTela("NomeTela");
//		dto.setNumeroTela(1);
//		dto.setTituloTela("Titulo tela");
//		dto.setUrlTela("urlTela");
//
//		RegistroAuditoriaDTO r = new RegistroAuditoriaDTO();
//		r.setDataCadastro(new Date());
//
//		MetaDadoDTO dtoMetadado = new MetaDadoDTO();
//		dtoMetadado.setVersao(1L);
//		dtoMetadado.setXml("xml");
//		dtoMetadado.setXhtml("xhtml");
//		dtoMetadado.setRegistroAuditoria(r);
//
//		dto.setMetadado(dtoMetadado);
//
//	}
//
//	private static void testeMetadado() throws InfraEstruturaException {
//		RegistroAuditoriaDTO r = new RegistroAuditoriaDTO();
//		r.setDataCadastro(new Date());
//
//		MetaDadoDTO dto = new MetaDadoDTO();
//		dto.setVersao(1L);
//		dto.setXml("xml");
//		dto.setXhtml("xhtml");
//		dto.setRegistroAuditoria(r);
//
//		MetaDado entidade = FabricaGenericaDados.transferirDados(MetaDado.class, dto);
//		System.out.println(entidade.getId());
//		System.out.println(entidade.getVersao());
//		System.out.println(entidade.getXml());
//
//	}
//
//	private static void testParametro() throws InfraEstruturaException {
//
//		ParametroDTO dto = new ParametroDTO();
//		dto.setId(1L);
//		dto.setNome("nome");
//		dto.setDataAlteracao(new Date());
//		dto.setDataInclusao(new Date());
//		dto.setDescricao("descricao");
//		dto.setTipoParametro(TipoParametroDTO.CARACTER);
//		Parametro entidade = FabricaGenericaDados.transferirDados(Parametro.class, dto);
//		System.out.println(entidade.getId());
//		System.out.println(entidade.getNome());
//		System.out.println(entidade.getDataAlteracao());
//		System.out.println(entidade.getDescricao());
//		System.out.println(entidade.getTipoParametro());
//
//		ParametroDTO dtoConvertido = FabricaGenericaDados.transferirDados(ParametroDTO.class, entidade);
//
//		System.out.println(dtoConvertido.getId());
//		System.out.println(dtoConvertido.getNome());
//		System.out.println(dtoConvertido.getDataAlteracao());
//		System.out.println(dtoConvertido.getDescricao());
//		System.out.println(dtoConvertido.getTipoParametro());
//	}
//
//	public static void testUsuario() throws InfraEstruturaException {
//
//		RegistroAuditoriaDTO r = new RegistroAuditoriaDTO();
//		r.setDataCadastro(new Date());
//
//		SenhaDTO senhaDTO = new SenhaDTO();
//		senhaDTO.setHashSenha("=333333333333333333");
//		senhaDTO.setStatusSenha(StatusSenhaDTO.ATIVO);
//		senhaDTO.setRegistroAuditoria(r);
//
//		List<SenhaDTO> senhas = new ArrayList<SenhaDTO>();
//		senhas.add(senhaDTO);
//
//		ContatoDTO contatoDTO = new ContatoDTO();
//		contatoDTO.setTipoContato(TipoContatoDTO.CELULAR);
//		contatoDTO.setValor("99999999999999999");
//		contatoDTO.setRegistroAuditoria(r);
//
//		List<ContatoDTO> contatos = new ArrayList<ContatoDTO>();
//		contatos.add(contatoDTO);
//
//		UsuarioDTO usuarioDTO = new UsuarioDTO();
//
//		usuarioDTO.setNome("x");
//		usuarioDTO.setSobrenome("y");
//		usuarioDTO.setStatusUsuario(StatusUsuarioDTO.ATIVO);
//		usuarioDTO.setContatos(contatos);
//		usuarioDTO.setSenhas(senhas);
//		usuarioDTO.setRegistroAuditoria(r);
//
//		Usuario entidade = FabricaGenericaDados.transferirDados(Usuario.class, usuarioDTO);
//
//		System.out.println(entidade.getId());
//		System.out.println(entidade.getNome());
//	}
}
