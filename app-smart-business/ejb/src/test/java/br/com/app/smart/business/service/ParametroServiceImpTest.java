package br.com.app.smart.business.service;



import java.io.File;
import java.util.Date;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.app.smart.business.dto.ParametroDTO;
import br.com.app.smart.business.dto.TipoParametroDTO;
import br.com.app.smart.business.util.PackageUtil;

@RunWith(Arquillian.class)
public class ParametroServiceImpTest {

	@Deployment
	public static Archive<?> createTestArchive() {

		PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml");

		File[] libs = pom.resolve("br.com.app.smart.business:app-smart-business-common:0.0.1-SNAPSHOT")
				.withClassPathResolution(true).withTransitivity().asFile();

		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war").addAsLibraries(libs)
				.addPackage(PackageUtil.BUILDER_INFRA.getPackageName())
				.addPackage(PackageUtil.CONVERSORES.getPackageName()).addPackage(PackageUtil.ENUMS.getPackageName())
				.addPackage(PackageUtil.EXCEPTION.getPackageName()).addPackage(PackageUtil.MODEL.getPackageName())
				.addPackage(PackageUtil.SERVICE.getPackageName()).addPackage(PackageUtil.UTIL.getPackageName())
				.addPackage(PackageUtil.DATA.getPackageName())
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource("test-ds.xml", "test-ds.xml");

		System.out.println(war.toString(true));

		return war;
	}

	@Inject
	ParametroServiceImp parametroServiceImp;

	@Inject
	Logger log;

	@Test
	public void registrarParametro() throws Exception {

		ParametroDTO dto = new ParametroDTO();
		dto.setId(1);
		dto.setNome("nome");
		dto.setDataAlteracao(new Date());
		dto.setDataInclusao(new Date());
		dto.setDescricao("descricao");
		dto.setTipoParametro(TipoParametroDTO.CARACTER);
		parametroServiceImp.registrar(dto);

		System.out.println("Adiconado com Sucesso");
	}

}
