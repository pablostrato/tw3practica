package ar.edu.unlam.tallerweb1.persistencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.dao.UsuarioDao;
import ar.edu.unlam.tallerweb1.modelo.Barrio;
import ar.edu.unlam.tallerweb1.modelo.Comuna;
import ar.edu.unlam.tallerweb1.modelo.Direccion;
import ar.edu.unlam.tallerweb1.modelo.Farmacia;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

public class UsuarioTest extends SpringTest{
	
		
		@Autowired
		private UsuarioDao dao;
		
		@Test
		@Transactional
		@Rollback
		public void guardar() {
			
			Usuario usuario = new Usuario();
			
			dao.guardar(usuario);
			
			Usuario buscado=dao.buscarPor(usuario.getId());
			assertThat(buscado).isNotNull();
		}
		//crear usuario ,lo guardo, lo modifico, los busco,
		
		@Test
		public void pruebaGuardar() {
			
			//dao.guardar(new Usuario("pedro@gmail.com","1234"));
			//dao.guardar(new Usuario("seba@gmail.com","1234"));
			
			List<Usuario>buscados=dao.buscarPorChar("1234");
			assertThat(buscados).hasSize(2);
			
		}
		
		@Test
		public void pruebaCriteria() {
			
			//dao.guardar(new Usuario("pedro@gmail.com","1234"));
			//dao.guardar(new Usuario("seba@gmail.com","1234"));
			
			//Session session = sessionFactory.getCurrentSession();
			
		}
		
		@Test
		@Transactional
		@Rollback
		public void buscarUsuario() {
			
			//Los get solo buscan por Id
			Usuario usuario=new Usuario();
			usuario.setEmail("seba@gmail.com");
			usuario.setPassword("1234");
			usuario.setRol("ADMIN");
			getSession().save(usuario);
			
			
			Usuario usuario2=new Usuario();
			usuario2.setEmail("andres@gmail.com");
			usuario2.setPassword("1234");
			usuario2.setRol("ADMIN");
			getSession().save(usuario2);
			
			Usuario ubd = getSession().get(Usuario.class, usuario.getId());
			
			List<Usuario> listaAdmin=getSession().createCriteria(Usuario.class)
					.add(Restrictions.eq("rol", "ADMIN"))
					.add(Restrictions.eq("email", "seba@gmail.com"))
					.list();
			
		}
		
		@Test
		@Transactional
		@Rollback
		public void TestFamacia() {
			
			Farmacia farmacia = new Farmacia();
			farmacia.setNombre("Geringa");
			getSession().save(farmacia);
			
			Comuna comuna = new Comuna();
			comuna.setNombre("Fuerte Apache");
			getSession().save(comuna);
			
			Barrio barrio = new Barrio();
			barrio.setNombre("La Boca");
			getSession().save(barrio);
			
			Direccion direccion = new Direccion();
			direccion.setCalle("Av Luro");
			direccion.setNumero(1234);
			getSession().save(direccion);
			
			
			
			
			List<Farmacia> listaFarmadeDir=
					getSession().createCriteria(Farmacia.class)
					.add(Restrictions.eq("direccion", direccion))
					.list();
			
			List<Farmacia> listaFarmadefv1900=
					getSession().createCriteria(Farmacia.class)
					.createAlias("direccion", "diccionbuscada")
					.add(Restrictions.eq("direccionbuscada.calle", "fv"))
					.add(Restrictions.eq("direccionbuscada.numero", 1900))
					.list();
			
			assertThat(listaFarmadefv1900.size()).isEqualTo(1);
			
			List<Farmacia> listaBuscada=
					getSession().createCriteria(Farmacia.class)
					.createAlias("direccion", "diccionbuscada")
					.add(Restrictions.eq("direccionbuscada.calle", "fv"))
					.add(Restrictions.eq("direccionbuscada.numero", 1900))
					.createAlias("direccionbuscada.barrio", "")
					.list();
		}

}
