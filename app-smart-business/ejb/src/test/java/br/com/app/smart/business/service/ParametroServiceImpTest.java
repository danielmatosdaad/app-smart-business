package br.com.app.smart.business.service;

import java.io.File;
import java.util.Date;
import java.util.List;
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

import br.com.app.smart.business.dto.MetaDadoDTO;
import br.com.app.smart.business.dto.ParametroDTO;
import br.com.app.smart.business.dto.TipoParametroDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.util.PackageUtil;

@RunWith(Arquillian.class)
public class ParametroServiceImpTest {

	@Deployment
	public static Archive<?> createTestArchive() {

		PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml");

		File[] libs = pom.resolve("br.com.app.smart.business:app-smart-business-common:0.0.1-SNAPSHOT")
				.withClassPathResolution(true).withTransitivity().asFile();

		File[] libs2 = pom.resolve("org.modelmapper:modelmapper:0.7.5").withClassPathResolution(true).withTransitivity()
				.asFile();
		
		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war").addAsLibraries(libs).addAsLibraries(libs2)
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

	@EJB(beanName = "ParametroServiceImp", beanInterface = IServicoRemoteDAO.class)
	private IServicoRemoteDAO<ParametroDTO> parametroServiceImp;

	@Inject
	private Logger log;

	@Test
	public void crud() {

		try {

			List<ParametroDTO> listaRemover = parametroServiceImp.bustarTodos();
			for (ParametroDTO item : listaRemover) {
				parametroServiceImp.remover(item);
			}
			
			ParametroDTO dto = new ParametroDTO();
			dto.setId(null);
			dto.setNome("nome");
			dto.setDataAlteracao(new Date());
			dto.setDataInclusao(new Date());
			dto.setDescricao("descricao");
			dto.setTipoParametro(TipoParametroDTO.CARACTER);

			ParametroDTO dto2 = new ParametroDTO();
			dto2.setId(null);
			dto2.setNome("nome2");
			dto2.setDataAlteracao(new Date());
			dto2.setDataInclusao(new Date());
			dto2.setDescricao("descricao2");
			dto2.setTipoParametro(TipoParametroDTO.NUMERAL);

			dto = parametroServiceImp.adiconar(dto);

			ParametroDTO resutaldoBusca = parametroServiceImp.bustarPorID(dto.getId());
			Assert.assertNotNull(resutaldoBusca);

			dto2 = parametroServiceImp.adiconar(dto2);
			ParametroDTO resutaldoBusca2 = parametroServiceImp.bustarPorID(dto2.getId());
			Assert.assertNotNull(resutaldoBusca2);

			List<ParametroDTO> todos = parametroServiceImp.bustarTodos();
			Assert.assertNotNull(todos);
			Assert.assertTrue(todos.size() == 2);

			int range[] = { 0, 2 };
			List<ParametroDTO> todosIntervalo = parametroServiceImp.bustarPorIntervaloID(range);
			Assert.assertNotNull(todosIntervalo);
			Assert.assertTrue(todosIntervalo.size() == 2);

			resutaldoBusca2.setNome("nome alterado");

			ParametroDTO resutaldoBusca3 = parametroServiceImp.alterar(resutaldoBusca2);
			Assert.assertEquals(resutaldoBusca2.getNome(), resutaldoBusca3.getNome());

			parametroServiceImp.remover(resutaldoBusca3);

			ParametroDTO dto4 = parametroServiceImp.bustarPorID(100L);
			Assert.assertNull(dto4);

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
			ParametroDTO dto = parametroServiceImp.bustarPorID(100L);
			Assert.assertNull(dto);

		} catch (InfraEstruturaException e) {
			e.printStackTrace();
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
