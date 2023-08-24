package br.com.fiap.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_TIPO_DE_ACAO", uniqueConstraints = {
        @UniqueConstraint(name = "UK_NM_TIPO_DE_ACAO", columnNames = {"NM_TIPO_DE_ACAO"}),
})
public class TipoDeAcao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TIPO_DE_ACAO")
    @SequenceGenerator(name = "SQ_TIPO_DE_ACAO", sequenceName = "SQ_TIPO_DE_ACAO")
    @Column(name = "ID_TIPO_DE_ACAO")
    private Long id;

    @Column(name = "NM_TIPO_DE_ACAO", nullable = false, unique = true)
    private String nome;

    public TipoDeAcao() {
    }

    public TipoDeAcao(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }


    public Long getId() {
        return id;
    }

    public TipoDeAcao setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public TipoDeAcao setNome(String nome) {
        this.nome = nome;
        return this;
    }


    @Override
    public String toString() {
        return "TipoDeAcao{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
