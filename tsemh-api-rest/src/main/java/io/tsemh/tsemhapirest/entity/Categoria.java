package io.tsemh.tsemhapirest.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name="tb_categoria")
public class Categoria {

	@Id
	@Column(name="id_categoria")
	@SequenceGenerator(name="categoria", sequenceName="sq_tb_categoria", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="categoria")
	private long id;
	
    @ManyToOne
    @JoinColumn(name="id_usuario")
    @JsonBackReference(value="categorias")
	private Usuario usuario;
	
	@Column(name="tp_categoria",nullable=false, length=50)
	private String  tipo;
	
	@Column(name="tt_categoria",nullable=false, length=50)
	private String  titulo;	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria", fetch = FetchType.LAZY)
	@JsonManagedReference(value="categoria")
	private List<Registro> registros;
	
	public Categoria() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Categoria(Usuario usuario, String tipo, String titulo, String link, List<Registro> registros) {
		super();
		this.id = 1;
		this.usuario = usuario;
		this.tipo = tipo;
		this.titulo = titulo;
		this.registros = registros;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@JsonManagedReference
	public List<Registro> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}
}
