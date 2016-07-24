package br.com.app.smart.business.service;

import java.util.logging.Logger;

import javax.inject.Named;

@Named
public class LogUtil {

	private static final String PROCESSANDO = "processando";
	private static final String SUCESSO = "sucesso";
	private static final String ERRO = "erro";

	public static void printProcessando(Logger log, Class clazz, Object... lista) {

		if (log != null && clazz != null) {

			log.info("-------" + PROCESSANDO + " " + clazz.getName() + " -------------------------");

			if (lista != null) {
				for (Object object : lista) {
					log.info("[" + object.toString() + "]");
				}
			}

		}

	}

	public static void printSucesso(Logger log, Class clazz) {
		if (log != null && clazz != null) {
			log.info("-------" + SUCESSO + " " + clazz.getName() + " -------------------------");
		}

	}

	public static void printErro(Logger log, Class clazz) {
		if (log != null && clazz != null) {
			log.info("-------" + ERRO + " " + clazz.getName() + " -------------------------");
		}
	}
}