package br.com.app.smart.business.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.logging.processor.util.Objects.ToStringBuilder;

@Entity(name = "parametro")
@XmlRootElement
@Table(name = "parametro", uniqueConstraints = @UniqueConstraint(columnNames = "nome"))
public class Parametro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private String nome;
	private String descricao;
	
	@Temporal(TemporalType.DATE)
	private Date dataInclusao;
	
	@Temporal(TemporalType.DATE)
	private Date dataAlteracao;

	@Enumerated(EnumType.STRING)
	private TipoParametro tipoParametro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoParametro getTipoParametro() {
		return tipoParametro;
	}

	public void setTipoParametro(TipoParametro tipoParametro) {
		this.tipoParametro = tipoParametro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	@Override
	public String toString() {
		System.out.println(this.getId());
		System.out.println(this.getNome());
		System.out.println(this.getDataAlteracao());
		System.out.println(this.getDescricao());
		System.out.println(this.getTipoParametro());
		return super.toString();
	}

}
