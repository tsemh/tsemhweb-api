package io.tsemh.tsemhapirest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name="tb_arquivos")
public class Arquivo {
	
	@Id
	@Column(name="id_arquivo")
	@SequenceGenerator(name="arquivo", sequenceName="sq_tb_arquivo", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="arquivo")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="id_registro")
    @JsonBackReference(value="registro")
	private Registro registro;
	
	@Column(name="nm_arquivo")
	private String nome;
	
	@Column(name="ds_arquivo")
	private String descricao;
	
	@Column(name="tp_arquivo")
	private String tipo;
	
	@Column(name="cm_arquivo")
	private String caminho;

	public Arquivo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Arquivo(long id, Registro registro, String nome, String descricao, String tipo, String caminho) {
		super();
		this.id = id;
		this.registro = registro;
		this.nome = nome;
		this.descricao = descricao;
		this.tipo = tipo;
		this.caminho = caminho;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonBackReference
	public Registro getRegistro() {
		return registro;
	}

	public void setRegistro(Registro registro) {
		this.registro = registro;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCaminho() {
		return caminho;
	}
	
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
}
