package io.tsemh.tsemhapirest.entity;

import java.sql.Clob;
import java.sql.NClob;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name="tb_registro")
public class Registro {
	
	@Id
	@Column(name="id_registro")
	@SequenceGenerator(name="registro", sequenceName="sq_tb_registro", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="registro")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
    @JsonBackReference(value="usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="id_categoria")
	@JsonBackReference(value="categoria")
	private Categoria categoria;
	
	@Column(name="tp_registro")
	private String tipo;
	
	@Column(name="nm_registro")
	private String nome;
	
	@Column(name="lk_registro")
	private String link;
	
	@Column(name = "ds_registro")
	private String descricao;
	
    @CreationTimestamp
    @Column(name = "dt_criacao")
    private LocalDateTime dataCriacao;
    
    @Column(name = "dq_registro")
    private Boolean destaque;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "registro", fetch = FetchType.LAZY)
	@JsonManagedReference(value="registro")
	private List<Arquivo> arquivos;

	public Registro() {
	}

	public Registro(long id, Usuario usuario, Categoria categoria, String tipo, String nome, String link,
			String descricao, LocalDateTime dataCriacao, Boolean destaque, List<Arquivo> arquivos) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.categoria = categoria;
		this.tipo = tipo;
		this.nome = nome;
		this.link = link;
		this.descricao = descricao;
		this.dataCriacao = dataCriacao;
		this.destaque = destaque;
		this.arquivos = arquivos;
	}

	public Boolean isDestaque() {
		return destaque;
	}

	public void setDestaque(Boolean destaque) {
		this.destaque = destaque;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonBackReference
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@JsonBackReference
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@JsonManagedReference
	public List<Arquivo> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<Arquivo> arquivos) {
		this.arquivos = arquivos;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	@PrePersist
	public void prePersist() {
		dataCriacao = LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime();
	}

}
