package br.com.app.smart.business.builder.infra;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Logger;

import br.com.app.smart.business.dto.ContatoDTO;
import br.com.app.smart.business.dto.ParametroDTO;
import br.com.app.smart.business.dto.RegistroAuditoriaDTO;
import br.com.app.smart.business.dto.SenhaDTO;
import br.com.app.smart.business.dto.StatusSenhaDTO;
import br.com.app.smart.business.dto.StatusUsuarioDTO;
import br.com.app.smart.business.dto.TipoContatoDTO;
import br.com.app.smart.business.dto.TipoParametroDTO;
import br.com.app.smart.business.dto.UsuarioDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.model.Parametro;
import br.com.app.smart.business.model.Usuario;

@Named
public class FabricaGenericaDados {

	@Inject
	private static Logger log;
	private Class<?> classe;

	public static <T> T criarInstancia(Class<T> clazz, Object... objetos) throws InfraEstruturaException {

		try {

			List<Class> classesParametros = new ArrayList<Class>();

			for (Object object : objetos) {

				classesParametros.add(object.getClass());
			}

			Class[] arrayClass = (Class[]) classesParametros.toArray();
			Constructor construtor = clazz.getDeclaredConstructor(arrayClass);
			T instancia = (T) construtor.newInstance(objetos);

		} catch (Exception e) {
			log.log(java.util.logging.Level.SEVERE, "", e);
			throw new InfraEstruturaException(e);
		}
		return null;

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

					Object lista = tratarLista(instancia, field, value);
					//System.out.println(field.getName() + "=" + lista);
					setDado(instancia, field.getName(), lista, instancia.getClass());
					continue;

				}

				if (value != null) {
					//System.out.println(field.getName() + "=" + value);
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
		//System.out.println("O atributo eh uma lista");
		Field fieldInstance = instancia.getClass().getDeclaredField(field.getName());
		ParameterizedType genericType = (ParameterizedType) fieldInstance.getGenericType();
		Class<?> parametroLista = (Class<?>) genericType.getActualTypeArguments()[0];
		//System.out.println("Tipo da lista: " + parametroLista);

		List listQueReceberaDados = createListOfType(parametroLista);
		List listAserTransferida = (List) value;

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

		Integer valor = (Integer) get(value, "value");
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

				if (object.getClass().getDeclaredField(fieldName).getType().getName()
						.equals(fieldValue.getClass().getName())) {

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

	public static void main(String args[]) throws InfraEstruturaException {

		testParametro();
		testUsuario();

	}

	private static void testParametro() throws InfraEstruturaException {

		ParametroDTO dto = new ParametroDTO();
		dto.setId(1L);
		dto.setNome("nome");
		dto.setDataAlteracao(new Date());
		dto.setDataInclusao(new Date());
		dto.setDescricao("descricao");
		dto.setTipoParametro(TipoParametroDTO.CARACTER);
		Parametro entidade = FabricaGenericaDados.transferirDados(Parametro.class, dto);
		System.out.println(entidade.getId());
		System.out.println(entidade.getNome());
		System.out.println(entidade.getDataAlteracao());
		System.out.println(entidade.getDescricao());
		System.out.println(entidade.getTipoParametro());

		ParametroDTO dtoConvertido = FabricaGenericaDados.transferirDados(ParametroDTO.class, entidade);

		System.out.println(dtoConvertido.getId());
		System.out.println(dtoConvertido.getNome());
		System.out.println(dtoConvertido.getDataAlteracao());
		System.out.println(dtoConvertido.getDescricao());
		System.out.println(dtoConvertido.getTipoParametro());
	}

	public static void testUsuario() throws InfraEstruturaException {

		RegistroAuditoriaDTO r = new RegistroAuditoriaDTO();
		r.setDataCadastro(new Date());

		SenhaDTO senhaDTO = new SenhaDTO();
		senhaDTO.setHashSenha("=333333333333333333");
		senhaDTO.setStatusSenha(StatusSenhaDTO.ATIVO);
		senhaDTO.setRegistroAuditoria(r);

		List<SenhaDTO> senhas = new ArrayList<SenhaDTO>();
		senhas.add(senhaDTO);

		ContatoDTO contatoDTO = new ContatoDTO();
		contatoDTO.setTipoContato(TipoContatoDTO.CELULAR);
		contatoDTO.setValor("99999999999999999");
		contatoDTO.setRegistroAuditoria(r);

		List<ContatoDTO> contatos = new ArrayList<ContatoDTO>();
		contatos.add(contatoDTO);

		UsuarioDTO usuarioDTO = new UsuarioDTO();

		usuarioDTO.setNome("x");
		usuarioDTO.setSobrenome("y");
		usuarioDTO.setStatusUsuario(StatusUsuarioDTO.ATIVO);
		usuarioDTO.setContatos(contatos);
		usuarioDTO.setSenhas(senhas);
		usuarioDTO.setRegistroAuditoria(r);

		Usuario entidade = FabricaGenericaDados.transferirDados(Usuario.class, usuarioDTO);

		System.out.println(entidade.getId());
		System.out.println(entidade.getNome());
	}
}
