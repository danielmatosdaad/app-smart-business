package br.com.app.smart.business.service;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;
import javax.ejb.EJB;
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

import br.com.app.smart.business.databuilder.PerfilBuilder;

import br.com.app.smart.business.databuilder.PerfilBuilder.TipoPerfilBuilder;
import br.com.app.smart.business.dto.PerfilDTO;
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
public class PerfilServiceImpTest {

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

	@EJB(beanName = "PerfilServiceImp", beanInterface = IServicoRemoteDAO.class)
	private IServicoRemoteDAO<PerfilDTO> remote;

	@EJB(beanName = "PerfilServiceImp", beanInterface = IServicoLocalDAO.class)
	private IServicoLocalDAO<PerfilDTO> local;

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

	
		// testando inserao
		PerfilDTO dto = PerfilBuilder.getInstanceDTO(TipoPerfilBuilder.INSTANCIA);
		PerfilDTO dto2 = PerfilBuilder.getInstanceDTO(TipoPerfilBuilder.INSTANCIA);
		dto = this.local.adiconar(dto);
		dto2 = this.local.adiconar(dto2);
		PerfilDTO resutaldoBusca = this.local.bustarPorID(dto.getId());
		Assert.assertNotNull(resutaldoBusca);
		Assert.assertEquals(dto.getId().longValue(), resutaldoBusca.getId().longValue());
		PerfilDTO resutaldoBusca2 = this.local.bustarPorID(dto2.getId());
		Assert.assertNotNull(resutaldoBusca2);
		Assert.assertEquals(dto2.getId().longValue(), resutaldoBusca2.getId().longValue());

		List<PerfilDTO> todos = local.bustarTodos();
		Assert.assertNotNull(todos);
		System.out.println("Buscar todos: " + todos.size());
		

		int range[] = { 0, 2 };
		List<PerfilDTO> todosIntervalo = local.bustarPorIntervaloID(range);
		Assert.assertNotNull(todosIntervalo);
		System.out.println("bustarPorIntervaloID: " + todosIntervalo.size());
		Assert.assertTrue(todosIntervalo.size() == 2);

		resutaldoBusca2.setDescricao("xml2");

		PerfilDTO resutaldoBusca3 = local.alterar(resutaldoBusca2);
		Assert.assertEquals(resutaldoBusca2.getDescricao(), resutaldoBusca3.getDescricao());

		local.remover(resutaldoBusca3);

		PerfilDTO dto4 = local.bustarPorID(resutaldoBusca3.getId());
		Assert.assertNull(dto4);

	

	}

	@Test
	public void testeCriarPerfilFuncinalidadeVinculadoso() throws InfraEstruturaException, NegocioException {

		// limpando a base
		List<PerfilDTO> listaRemover = this.local.bustarTodos();
		for (PerfilDTO item : listaRemover) {
			this.local.remover(item);
		}

		System.out.println("sucesso testeCriarPerfilFuncinalidadeVinculadoso");
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

	@Test
	public void testeArvore() throws InfraEstruturaException, NegocioException {
		System.out.println("-----------------------testeArvore teste construcao---------------------");
		// limpando a base
		List<PerfilDTO> listaRemover = this.local.bustarTodos();
		for (PerfilDTO item : listaRemover) {
			this.local.remover(item);
		}
		// do raiz null
		// noRaiz
		// / 1 \
		// / \
		// / \
		// / \
		// noSubRaizN1E noSubRaizN1D
		// / 2 \ / 3 \
		// / \ / \
		// / \ / \
		// noSubRaizN2EE noSubRaizN2ED noSubRaizN2DE noSubRaizN2DD
		// 4 5 6 7
		PerfilDTO noRaiz = PerfilBuilder.getInstanceDTO(TipoPerfilBuilder.INSTANCIA);
		
		printDTO(noRaiz);
		noRaiz = this.local.adiconar(noRaiz);
		PerfilDTO resutaldoNoRaiz = this.local.bustarPorID(noRaiz.getId());

		Assert.assertNotNull(resutaldoNoRaiz);
		Assert.assertEquals(noRaiz.getId().longValue(), resutaldoNoRaiz.getId().longValue());

		System.out.println("noRaiz id" + noRaiz.getId());
		Assert.assertNull(noRaiz.getPerfilPai());
		
		System.out.println("--------------------------------------------------------------------------------");


		PerfilDTO noSubRaizN1E = PerfilBuilder.getInstanceDTO(TipoPerfilBuilder.INSTANCIA);
		noSubRaizN1E.setPerfilPai(noRaiz);
		printDTO(noSubRaizN1E);
		noSubRaizN1E = this.local.adiconar(noSubRaizN1E);

		PerfilDTO resutaldoNoSubRaizN1E = this.local.bustarPorID(noSubRaizN1E.getId());

		Assert.assertNotNull(resutaldoNoSubRaizN1E);
		Assert.assertEquals(noSubRaizN1E.getId().longValue(), resutaldoNoSubRaizN1E.getId().longValue());

		System.out.println("noSubRaizN1E id" + resutaldoNoSubRaizN1E.getId());
		System.out.println("noSubRaizN1E idPai");
		if(resutaldoNoSubRaizN1E.getPerfilPai()==null){
			
			System.out.println("noSubRaizN1E idPai null");
		}else{
			
			System.out.println("noSubRaizN1E idPai" + resutaldoNoSubRaizN1E.getPerfilPai().getId());
		}
		
		System.out.println("--------------------------------------------------------------------------------");

		PerfilDTO noSubRaizN1D = PerfilBuilder.getInstanceDTO(TipoPerfilBuilder.INSTANCIA);

		noSubRaizN1D.setPerfilPai(noRaiz);
		printDTO(noSubRaizN1D);
		noSubRaizN1D = this.local.adiconar(noSubRaizN1D);

		PerfilDTO resutaldoNoSubRaizN1D = this.local.bustarPorID(noSubRaizN1D.getId());

		Assert.assertNotNull(resutaldoNoSubRaizN1D);
		Assert.assertEquals(noSubRaizN1D.getId().longValue(), resutaldoNoSubRaizN1D.getId().longValue());

		System.out.println("noSubRaizN1D id" + resutaldoNoSubRaizN1D.getId());
		System.out.println("noSubRaizN1D idPai" + resutaldoNoSubRaizN1D.getPerfilPai() == null ? null
				: resutaldoNoSubRaizN1D.getPerfilPai().getId());

		System.out.println("--------------------------------------------------------------------------------");


		PerfilDTO noSubRaizN2EE = PerfilBuilder.getInstanceDTO(TipoPerfilBuilder.INSTANCIA);

		noSubRaizN2EE.setPerfilPai(noSubRaizN1E);
		printDTO(noSubRaizN2EE);
		noSubRaizN2EE = this.local.adiconar(noSubRaizN2EE);

		PerfilDTO resutaldoNoSubRaizN2EE = this.local.bustarPorID(noSubRaizN2EE.getId());

		Assert.assertNotNull(resutaldoNoSubRaizN2EE);
		Assert.assertEquals(noSubRaizN2EE.getId().longValue(), resutaldoNoSubRaizN2EE.getId().longValue());

		System.out.println("noSubRaizN2EE id" + resutaldoNoSubRaizN2EE.getId());
		System.out.println("noSubRaizN2EE idPai" + resutaldoNoSubRaizN2EE.getPerfilPai() == null ? null
				: resutaldoNoSubRaizN2EE.getPerfilPai().getId());

		System.out.println("--------------------------------------------------------------------------------");

		PerfilDTO noSubRaizN2ED = PerfilBuilder.getInstanceDTO(TipoPerfilBuilder.INSTANCIA);
		noSubRaizN2ED.setPerfilPai(noSubRaizN1E);
		printDTO(noSubRaizN2ED);
		noSubRaizN2ED = this.local.adiconar(noSubRaizN2ED);

		PerfilDTO resutaldoNoSubRaizN2ED = this.local.bustarPorID(noSubRaizN2ED.getId());

		Assert.assertNotNull(resutaldoNoSubRaizN2ED);
		Assert.assertEquals(noSubRaizN2ED.getId().longValue(), resutaldoNoSubRaizN2ED.getId().longValue());

		System.out.println("noSubRaizN2ED id" + resutaldoNoSubRaizN2ED.getId());
		System.out.println("noSubRaizN2ED idPai" + resutaldoNoSubRaizN2ED.getPerfilPai() == null ? null
				: resutaldoNoSubRaizN2ED.getPerfilPai().getId());

		System.out.println("--------------------------------------------------------------------------------");


		PerfilDTO noSubRaizN2DE = PerfilBuilder.getInstanceDTO(TipoPerfilBuilder.INSTANCIA);

		noSubRaizN2DE.setPerfilPai(noSubRaizN1D);
		printDTO(noSubRaizN2DE);
		noSubRaizN2DE = this.local.adiconar(noSubRaizN2DE);

		PerfilDTO resutaldoNoSubRaizN2DE = this.local.bustarPorID(noSubRaizN2DE.getId());

		Assert.assertNotNull(resutaldoNoSubRaizN2DE);
		Assert.assertEquals(noSubRaizN2DE.getId().longValue(), resutaldoNoSubRaizN2DE.getId().longValue());

		System.out.println("noSubRaizN2DE id" + resutaldoNoSubRaizN2DE.getId());
		System.out.println("noSubRaizN2DE idPai" + resutaldoNoSubRaizN2DE.getPerfilPai() == null ? null
				: resutaldoNoSubRaizN2DE.getPerfilPai().getId());

		System.out.println("--------------------------------------------------------------------------------");

		PerfilDTO noSubRaizN2DD = PerfilBuilder.getInstanceDTO(TipoPerfilBuilder.INSTANCIA);
		noSubRaizN2DD.setPerfilPai(noSubRaizN1D);
		printDTO(noSubRaizN2DD);
		noSubRaizN2DD = this.local.adiconar(noSubRaizN2DD);

		PerfilDTO resutaldoNoSubRaizN2DD = this.local.bustarPorID(noSubRaizN2DD.getId());

		Assert.assertNotNull(resutaldoNoSubRaizN2DD);
		Assert.assertEquals(noSubRaizN2DD.getId().longValue(), resutaldoNoSubRaizN2DD.getId().longValue());

		System.out.println("noSubRaizN2DD id" + resutaldoNoSubRaizN2DD.getId());
		System.out.println("noSubRaizN2DD idPai" + resutaldoNoSubRaizN2DD.getPerfilPai() == null ? null
				: resutaldoNoSubRaizN2DD.getPerfilPai().getId());

		System.out.println("-----------------------FIM---------------------");
		System.out.println("-----------------------testeArvore - Teste Busca---------------------");

		// do raiz null
		// noRaiz
		// / 1 \
		// / \
		// / \
		// / \
		// noSubRaizN1E noSubRaizN1D
		// / 2 \ / 3 \
		// / \ / \
		// / \ / \
		// noSubRaizN2EE noSubRaizN2ED noSubRaizN2DE noSubRaizN2DD
		// 4 5 6 7

		List<PerfilDTO> resutaldoNoRaizArvore = this.local.bustarTodos();

		for (PerfilDTO perfilDTO : resutaldoNoRaizArvore) {

			if (perfilDTO.getId() == noRaiz.getId()) {

				List<PerfilDTO> listaFilhoNoRaiz = perfilDTO.getPerfilFilhos();
				Assert.assertNotNull(listaFilhoNoRaiz);
				System.out.println("Filho: listaFilhoNoRaiz.get(0)." + listaFilhoNoRaiz.get(0).getId());
				System.out.println("Filho: listaFilhoNoRaiz.get(1)." + listaFilhoNoRaiz.get(1).getId());
				Assert.assertEquals(perfilDTO.getPerfilFilhos().get(0).getId(), noSubRaizN1E.getId());
				Assert.assertEquals(perfilDTO.getPerfilFilhos().get(1).getId(), noSubRaizN1D.getId());
			}

			if (perfilDTO.getId() == noSubRaizN1E.getId()) {

				List<PerfilDTO> listaFilhoNoRaiz = perfilDTO.getPerfilFilhos();
				Assert.assertNotNull(listaFilhoNoRaiz);
				System.out.println("Filho: noSubRaizN1E.get(0)." + listaFilhoNoRaiz.get(0).getId());
				System.out.println("Filho: noSubRaizN1E.get(1)." + listaFilhoNoRaiz.get(1).getId());
				Assert.assertEquals(listaFilhoNoRaiz.get(0).getId(), noSubRaizN2EE.getId());
				Assert.assertEquals(listaFilhoNoRaiz.get(1).getId(), noSubRaizN2ED.getId());
			}

			if (perfilDTO.getId() == noSubRaizN1D.getId()) {

				List<PerfilDTO> listaFilhoNoRaiz = perfilDTO.getPerfilFilhos();
				Assert.assertNotNull(listaFilhoNoRaiz);
				
				System.out.println("Filho: noSubRaizN1D.get(0)." + listaFilhoNoRaiz.get(0).getId());
				System.out.println("Filho: noSubRaizN1D.get(1)." + listaFilhoNoRaiz.get(1).getId());
				Assert.assertEquals(listaFilhoNoRaiz.get(0).getId(), noSubRaizN2DE.getId());
				Assert.assertEquals(listaFilhoNoRaiz.get(1).getId(), noSubRaizN2DD.getId());
			}

		}

	}

	private void printDTO(PerfilDTO noSubRaizN1E) {
		System.out.println("PerfilDTO id: " + noSubRaizN1E.getId());
		if(noSubRaizN1E.getPerfilPai()!=null){
			
			System.out.println("PerfilDTO idPai : " + noSubRaizN1E.getPerfilPai().getId());
		}else{
			
			System.out.println("PerfilDTO idPai : null");
		}
	}

}
