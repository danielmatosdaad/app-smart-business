package br.com.app.smart.business.builder.infra;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.app.smart.business.dto.ParametroDTO;
import br.com.app.smart.business.dto.TipoParametroDTO;
import br.com.app.smart.business.model.Parametro;


public class FabricaGenericaDados<INSTANCIA> {

	private INSTANCIA instancia;
	private Class<?> classe;

	public FabricaGenericaDados(Class clazz, Object ...objetos) throws InstantiationException,
 IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

		List<Class> classesParametros = new ArrayList<Class>();
		
		for (Object object : objetos) {
			
			classesParametros.add(object.getClass());
			
		}
		
		Class[] arrayClass = (Class[]) classesParametros.toArray();
		Constructor  construtor = clazz.getDeclaredConstructor(arrayClass);
		instancia = (INSTANCIA) construtor.newInstance(objetos);
		classe = clazz;
	}

	public FabricaGenericaDados(Class clazz) throws InstantiationException,
			IllegalAccessException {

		instancia = (INSTANCIA) clazz.newInstance();
		classe = clazz;
	}

	@SuppressWarnings("unchecked")
	public INSTANCIA transferirDados(Object dadosParaTransferir)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException, SecurityException, NoSuchMethodException,
			InstantiationException, InvocationTargetException {

		for (Field field : dadosParaTransferir.getClass().getDeclaredFields()) {
			field.setAccessible(true);

			if (java.lang.reflect.Modifier.isStatic(field.getModifiers())
					|| java.lang.reflect.Modifier.isFinal(field.getModifiers())) {
				continue;
			}
			Object value = field.get(dadosParaTransferir);

			if (field.getType().isEnum()) {

				final Enum<?> theOneAndOnly = tratarEnum(field, value);
				value = theOneAndOnly;
			}

			if (value != null) {
				System.out.println(field.getName() + "=" + value);
				set(instancia, field.getName(), value);
			}
		}

		return instancia;
	}

	private Enum<?> tratarEnum(Field field, Object value)
			throws NoSuchFieldException {
		Integer valor = (Integer) get(value, "value");
		String nomeAtributo = field.getName();
		String nameEnum = value.toString();
		Field enumFild = instancia.getClass().getDeclaredField(nomeAtributo);
		final Class<? extends Enum> enumType = (Class<? extends Enum>) enumFild
				.getType();
		final Enum<?> theOneAndOnly = Enum.valueOf(enumType, nameEnum);
		return theOneAndOnly;
	}

	public boolean set(Object object, String fieldName, Object fieldValue) {
		Class<?> clazz = object.getClass();
		while (clazz != null) {
			try {
				Field field = clazz.getDeclaredField(fieldName);

				field.setAccessible(true);
				field.set(object, fieldValue);
				return true;
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			} catch (Exception e) {
				System.out.println(e.toString());
				return false;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public <V> V get(Object object, String fieldName) {
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

	public static void main(String args[]) throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			NoSuchFieldException, SecurityException, NoSuchMethodException,
			InvocationTargetException {

		FabricaGenericaDados<Parametro> fabrica = new FabricaGenericaDados<Parametro>(
				Parametro.class);

		ParametroDTO dto = new ParametroDTO();
		dto.setId(1);
		dto.setNome("nome");
		dto.setDataAlteracao(new Date());
		dto.setDataInclusao(new Date());
		dto.setDescricao("descricao");
		dto.setTipoParametro(TipoParametroDTO.CARACTER);
		Parametro entidade = fabrica.transferirDados(dto);
		System.out.println(entidade.getId());
		System.out.println(entidade.getNome());
		System.out.println(entidade.getDataAlteracao());
		System.out.println(entidade.getDescricao());
		System.out.println(entidade.getTipoParametro());

		FabricaGenericaDados<ParametroDTO> f = new FabricaGenericaDados<ParametroDTO>(
				ParametroDTO.class);

		ParametroDTO dtoConvertido = f.transferirDados(entidade);

		System.out.println(dtoConvertido.getId());
		System.out.println(dtoConvertido.getNome());
		System.out.println(dtoConvertido.getDataAlteracao());
		System.out.println(dtoConvertido.getDescricao());
		System.out.println(dtoConvertido.getTipoParametro());

	}
}
