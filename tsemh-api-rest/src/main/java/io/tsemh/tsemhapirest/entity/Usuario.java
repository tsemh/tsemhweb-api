package io.tsemh.tsemhapirest.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

@Entity
@Table(name="tb_usuario")
public class Usuario implements UserDetails{
    
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name="id_usuario")
    @SequenceGenerator(name="usuario", sequenceName="sq_tb_usuario", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="usuario")
    private long id;
    
    @Column(name="em_usuario")
    private String email;
    
    @Column(name = "sn_usuario", length = 100)
    private String senha;
    
    @Column(name="nm_usuario")
    private String nome;
    
    @Column(name="tt_usuario")
    private String titulo;
    
    @Column(name="ds_usuario")
    private String descricao;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Categoria> categorias = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Registro> registros = new ArrayList<>();

    public Usuario() {    
    }

    public Usuario(Integer id, String email, String senha, String nome, String titulo, String descricao,
    		 		List<Categoria> categorias, List<Registro> registros) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.titulo = titulo;
        this.descricao = descricao;
        this.categorias = categorias;
        this.registros = registros;
    }
    

    public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<Registro> getRegistros() {
        return registros;
    }

    public void setRegistros(List<Registro> registros) {
        this.registros = registros;
    }
    
	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_DONO"));
    }

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

    @Override
    public String toString() {
        return "Usuário:\n[id_usuario    =" +id  +  ",\n"
                            +"em_usuario =" +email     +  ",\n"
                            +"sn_usuario =" +senha  +  ",\n"
                            +"nm_usuario =" +nome  +  ",\n"
                            +"tt_usuario =" +titulo +  ",\n"

                +"]";
    }
}
