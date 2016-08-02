package br.com.app.smart.business.service;

import static org.junit.Assert.*;

import java.io.File;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import br.com.app.smart.business.databuilder.FuncionalidadeBuilder;

import br.com.app.smart.business.databuilder.FuncionalidadeBuilder.TipoFuncionalidadeBuilder;
import br.com.app.smart.business.dto.FuncionalidadeDTO;
import br.com.app.smart.business.dto.GrupoFuncionalidadeDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.interfaces.IServicoLocalDAO;
import br.com.app.smart.business.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.util.PackageUtil;

/**
 * @author daniel-matos
 *
 */
@RunWith(Arquillian.class)
public class FuncionalidadeServiceImpTest {

	@Deployment
	public static Archive<?> createTestArchive() {

		PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml");

		File[] libs = pom.resolve("br.com.app.smart.business:app-smart-business-common:0.0.1-SNAPSHOT")
				.withClassPathResolution(true).withTransitivity().asFile();

		File[] libs2 = pom.resolve("org.modelmapper:modelmapper:0.7.5").withClassPathResolution(true).withTransitivity()
				.asFile();

		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war").addAsLibraries(libs).addAsLibraries(libs2)
				.addPackage(PackageUtil.DATA_BUILDER.getPackageName())
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

	@EJB(beanName = "FuncionalidadeServiceImp", beanInterface = IServicoRemoteDAO.class)
	private IServicoRemoteDAO<FuncionalidadeDTO> remote;

	@EJB(beanName = "FuncionalidadeServiceImp", beanInterface = IServicoLocalDAO.class)
	private IServicoLocalDAO<FuncionalidadeDTO> local;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * 
	 * @throws NegocioException 
	 * @throws InfraEstruturaException 
	 * @see a) processo define-se em criar sempre persisitir tres registros b)
	 *      buscar os dois registros e verificar se os dois novos ids c) alterar
	 *      os dois registros e verificar se esses registros foram d) excluir
	 *      todos regristros
	 */
	@Test
	public void crud() throws InfraEstruturaException, NegocioException {

			//limpando a base
			List<FuncionalidadeDTO> listaRemover = this.local.bustarTodos();
			for (FuncionalidadeDTO item : listaRemover) {
				this.local.remover(item);
			}
			
			//testando inserao
			FuncionalidadeDTO dto = FuncionalidadeBuilder.getInstanceDTO(TipoFuncionalidadeBuilder.INSTANCIA);
			FuncionalidadeDTO dto2 = FuncionalidadeBuilder.getInstanceDTO(TipoFuncionalidadeBuilder.INSTANCIA);
			dto = this.local.adiconar(dto);
			dto2 = this.local.adiconar(dto2);
			FuncionalidadeDTO resutaldoBusca = this.local.bustarPorID(dto.getId());
			Assert.assertNotNull(resutaldoBusca);
			Assert.assertEquals(dto.getId().longValue(), resutaldoBusca.getId().longValue());
			FuncionalidadeDTO resutaldoBusca2 = this.local.bustarPorID(dto2.getId());
			Assert.assertNotNull(resutaldoBusca2);
			Assert.assertEquals(dto2.getId().longValue(), resutaldoBusca2.getId().longValue());
			
			List<FuncionalidadeDTO> todos = local.bustarTodos();
			Assert.assertNotNull(todos);
			System.out.println("Buscar todos: " + todos.size());
			Assert.assertTrue(todos.size() == 2);

			int range[] = { 0, 2 };
			List<FuncionalidadeDTO> todosIntervalo = local.bustarPorIntervaloID(range);
			Assert.assertNotNull(todosIntervalo);
			System.out.println("bustarPorIntervaloID: " + todosIntervalo.size());
			Assert.assertTrue(todosIntervalo.size() == 2);

			resutaldoBusca2.setDescricao("xml2");

			FuncionalidadeDTO resutaldoBusca3 = local.alterar(resutaldoBusca2);
			Assert.assertEquals(resutaldoBusca2.getDescricao(), resutaldoBusca3.getDescricao());

			local.remover(resutaldoBusca3);

			FuncionalidadeDTO dto4 = local.bustarPorID(resutaldoBusca3.getId());
			Assert.assertNull(dto4);

		}

	//
	@Test
	public void testTestServiceLocal() {
		assertNotNull(this.local);
		System.out.println(this.local);
		System.out.println(this.local.getClass());
	}

	// Success
	@Test
	public void testTestServiceRemote() {
		assertNotNull(this.remote);
		System.out.println(this.remote);
		System.out.println(this.remote.getClass());
	}

}
