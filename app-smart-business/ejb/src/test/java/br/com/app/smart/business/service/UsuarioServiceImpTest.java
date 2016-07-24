package br.com.app.smart.business.service;

import java.io.File;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.app.smart.business.databuilder.UsuarioBuilder;
import br.com.app.smart.business.dto.StatusUsuarioDTO;
import br.com.app.smart.business.dto.UsuarioDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.interfaces.IServicoPadraoBD;
import br.com.app.smart.business.util.PackageUtil;

@RunWith(Arquillian.class)
public class UsuarioServiceImpTest {

	@Deployment
	public static Archive<?> createTestArchive() {

		PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml");

		File[] libs = pom.resolve("br.com.app.smart.business:app-smart-business-common:0.0.1-SNAPSHOT")
				.withClassPathResolution(true).withTransitivity().asFile();

		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war").addAsLibraries(libs)
				.addPackage("br.com.app.smart.business.databuilder")
				.addPackage(PackageUtil.BUILDER_INFRA.getPackageName())
				.addPackage(PackageUtil.CONVERSORES.getPackageName()).addPackage(PackageUtil.ENUMS.getPackageName())
				.addPackage(PackageUtil.EXCEPTION.getPackageName()).addPackage(PackageUtil.MODEL.getPackageName())
				.addPackage(PackageUtil.SERVICE.getPackageName()).addPackage(PackageUtil.UTIL.getPackageName())
				.addPackage(PackageUtil.FACEDE.getPackageName()).addPackage(PackageUtil.DATA.getPackageName())
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource("test-ds.xml", "test-ds.xml");

		System.out.println(war.toString(true));

		return war;
	}

	@EJB(beanName = "UsuarioServiceImp", beanInterface = IServicoPadraoBD.class)
	private IServicoPadraoBD<UsuarioDTO> usuarioServiceImp;

	@Inject
	private Logger log;

	@Test
	public void crud() {

		try {

			// a) processo define-se em criar sempre persisitir tres registros
			// b) buscar os dois registros e verificar se os dois novos ids
			// c) alterar os dois registros e verificar se esses registros foram
			// alterados
			// d) excluir todos regristros
			UsuarioDTO usuarioDTO = UsuarioBuilder.getInstanceDTO(UsuarioBuilder.TipoUsuarioBuilder.USUARIO_COMPLETO);
			usuarioDTO = usuarioServiceImp.adiconar(usuarioDTO);
			UsuarioDTO resutaldoBusca = usuarioServiceImp.bustarPorID(usuarioDTO.getId());

			UsuarioDTO usuarioDTO2 = UsuarioBuilder.getInstanceDTO(UsuarioBuilder.TipoUsuarioBuilder.USUARIO_COMPLETO);
			usuarioDTO2 = usuarioServiceImp.adiconar(usuarioDTO2);
			UsuarioDTO resutaldoBusca2 = usuarioServiceImp.bustarPorID(usuarioDTO2.getId());

			UsuarioDTO usuarioDTO3 = UsuarioBuilder.getInstanceDTO(UsuarioBuilder.TipoUsuarioBuilder.USUARIO_COMPLETO);
			usuarioDTO3 = usuarioServiceImp.adiconar(usuarioDTO3);
			UsuarioDTO resutaldoBusca3 = usuarioServiceImp.bustarPorID(usuarioDTO3.getId());

			Assert.assertNotNull(resutaldoBusca);
			Assert.assertNotNull(resutaldoBusca2);
			Assert.assertNotNull(resutaldoBusca3);

			// verificar se todas as entidades fora persistidas

			Assert.assertNotNull(resutaldoBusca.getContatos());
			Assert.assertNotNull(resutaldoBusca.getSenhas());
			Assert.assertTrue(resutaldoBusca.getContatos().get(0).getId() >= 0);
			Assert.assertTrue(resutaldoBusca.getSenhas().get(0).getId() >= 0);

			Assert.assertNotNull(resutaldoBusca3.getContatos());
			Assert.assertNotNull(resutaldoBusca3.getSenhas());
			Assert.assertTrue(resutaldoBusca3.getContatos().get(0).getId() >= 0);
			Assert.assertTrue(resutaldoBusca3.getSenhas().get(0).getId() >= 0);

			// alterar

			UsuarioDTO usuarioAlterarDTO3 = usuarioServiceImp.bustarPorID(usuarioDTO3.getId());

			usuarioAlterarDTO3.setNome("alessandra");
			usuarioAlterarDTO3.setSobrenome("mendonca morais");
			usuarioAlterarDTO3.setStatusUsuario(StatusUsuarioDTO.EXCLUIDO);

			// o casdade eh apenas em persistence
			// verificar se alterando outros atributos nao serao desfeitos os
			// relacionamentos
			//usuarioAlterarDTO3.setSenhas(null);
			//usuarioAlterarDTO3.setContatos(null);

			UsuarioDTO alteradoDTO3 = usuarioServiceImp.alterar(usuarioAlterarDTO3);

			Assert.assertNotNull(alteradoDTO3.getId());
			Assert.assertNotNull(alteradoDTO3.getNome());
			Assert.assertNotNull(alteradoDTO3.getSobrenome());
			Assert.assertNotNull(alteradoDTO3.getStatusUsuario());

			Assert.assertTrue(alteradoDTO3.getId().longValue() == resutaldoBusca3.getId().longValue());
			Assert.assertTrue(alteradoDTO3.getNome().equals("alessandra"));
			Assert.assertTrue(alteradoDTO3.getSobrenome().equals("mendonca morais"));
			Assert.assertTrue(alteradoDTO3.getStatusUsuario().equals(StatusUsuarioDTO.EXCLUIDO));

			// o casdade eh apenas em persistence
			// verificar se alterando outros atributos nao serao desfeitos os
			// relacionamentos
			
			UsuarioDTO buscaAlteradoDTO3 = usuarioServiceImp.bustarPorID(alteradoDTO3.getId());
			Assert.assertNotNull(buscaAlteradoDTO3.getContatos());
			Assert.assertNotNull(buscaAlteradoDTO3.getSenhas());
			
			System.out.println(buscaAlteradoDTO3.getContatos());
			System.out.println(buscaAlteradoDTO3.getSenhas());
			
			Assert.assertTrue(buscaAlteradoDTO3.getContatos().get(0).getId() == resutaldoBusca3.getContatos().get(0).getId());
			Assert.assertTrue(buscaAlteradoDTO3.getSenhas().get(0).getId() == resutaldoBusca3.getSenhas().get(0).getId());
			
		    usuarioServiceImp.remover(usuarioAlterarDTO3);
		    
		    
		    UsuarioDTO removidoDTO3 = usuarioServiceImp.bustarPorID(usuarioAlterarDTO3.getId());
			Assert.assertNull(removidoDTO3);
		    

		} catch (InfraEstruturaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void buscarIdNaoExistente() {

		try {
			UsuarioDTO dto = usuarioServiceImp.bustarPorID(100L);
			Assert.assertNull(dto);

		} catch (InfraEstruturaException e) {
			e.printStackTrace();
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
