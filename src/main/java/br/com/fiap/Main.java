package br.com.fiap;

import br.com.fiap.domain.entity.Advogado;
import br.com.fiap.domain.entity.Estado;
import br.com.fiap.domain.entity.Processo;
import br.com.fiap.domain.entity.TipoDeAcao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("oracle");
        EntityManager manager = factory.createEntityManager();

        addEstado(manager);
        addAdvogado(manager);
        addTipoDeAcao(manager);
        addProcesso(manager);

        listaTodosProcessos(manager);
        listaProcessoPeloId(manager);

        manager.close();
        factory.close();
    }

    private static void listaProcessoPeloId(EntityManager manager) {
        String id_processo = JOptionPane.showInputDialog("Informe o id do processo que deseja visualizar:");
        Processo processo = manager.find(Processo.class, id_processo);
        JOptionPane.showMessageDialog(null,  processo.toString());
    }

    private static void listaTodosProcessos(EntityManager manager) {
        List<Processo> processos = manager.createQuery("FROM Processo").getResultList();
        JOptionPane.showInputDialog(
                null,
                "Listagem de todos os processos",
                "Processos",
                JOptionPane.QUESTION_MESSAGE,
                null,
                processos.toArray(),
                processos.get(0)
        );
    }

    private static void addProcesso(EntityManager manager) {
        Processo processo = new Processo();
        String numero_processo = JOptionPane.showInputDialog("Informe o número do processo: ");
        Boolean isProBono = JOptionPane.showInputDialog("O Processo é pró-bono? (1) SIM (2) Não: ").equals("1");

        List<Advogado> advogados = manager.createQuery("FROM Advogado").getResultList();
        Advogado advogadoSelecionado = (Advogado) JOptionPane.showInputDialog(
                null,
                "Selecione o Advogado",
                "Seleção de advogados",
                JOptionPane.QUESTION_MESSAGE,
                null,
                advogados.toArray(),
                advogados.get(0)
        );

        List<TipoDeAcao> tipoDeAcao = manager.createQuery("FROM TipoDeAcao").getResultList();
        TipoDeAcao tipoDeAcaoSelecionada = (TipoDeAcao) JOptionPane.showInputDialog(
                null,
                "Selecione o tipo da ação",
                "Seleção de Tipos de ação",
                JOptionPane.QUESTION_MESSAGE,
                null,
                tipoDeAcao.toArray(),
                tipoDeAcao.get(0)
        );

        processo
                .setNumero(numero_processo)
                .setProBono(isProBono)
                .setAdvogado(advogadoSelecionado)
                .setTipoDeAcao(tipoDeAcaoSelecionada);

        try {
            manager.getTransaction().begin();
            manager.persist(processo);
            manager.getTransaction().commit();
        } catch (Exception ex) {
            String error = """
                        Erro!
                         Não foi possível salvar os dados desta operação.
                                            
                        """ + ex.getMessage();
            System.err.println(error);
            JOptionPane.showMessageDialog(null, error);
        }
    }

    private static void addTipoDeAcao(EntityManager manager) {
        TipoDeAcao tipoDeAcao = new TipoDeAcao();
        String nome_tipo_de_acao = JOptionPane.showInputDialog("Informe o Tipo de ação: ");

        tipoDeAcao.setNome(nome_tipo_de_acao);

        try {
            manager.getTransaction().begin();
            manager.persist(tipoDeAcao);
            manager.getTransaction().commit();
        } catch (Exception ex) {
            String error = """
                        Erro!
                        Não foi possível salvar os dados desta operação.
                                            
                        """ + ex.getMessage();
            System.err.println(error);
            JOptionPane.showMessageDialog(null, error);
        }
    }

    private static void addAdvogado(EntityManager manager) {
        Advogado advogado = new Advogado();

        String nome_advogado = JOptionPane.showInputDialog("Informe o nome do Advogado: ");
        String oab_advogado = JOptionPane.showInputDialog("Informe o número da OAB do Advogado " + nome_advogado + ":");

        List<Estado> estados = manager.createQuery("FROM Estado").getResultList();
        Estado estadoSelecionado = (Estado) JOptionPane.showInputDialog(
                null,
                "Selecione o Estado",
                "Seleção de estados",
                JOptionPane.QUESTION_MESSAGE,
                null,
                estados.toArray(),
                estados.get(0)
        );

        advogado.setNome(nome_advogado).setNumeroOAB(oab_advogado).setEstado(estadoSelecionado);

        try {
            manager.getTransaction().begin();
            manager.persist(advogado);
            manager.getTransaction().commit();
        } catch (Exception ex) {
            String error = """
                        Erro!
                        Não foi possível salvar os dados desta operação.
                                            
                        """ + ex.getMessage();
            System.err.println(error);
            JOptionPane.showMessageDialog(null, error);
        }
    }

    private static void addEstado(EntityManager manager) {
        Estado estado = new Estado();

        String nome_estado = JOptionPane.showInputDialog("Informe o nome do Estado: ");
        String sigla_estado = JOptionPane.showInputDialog("Informe a sigla do Estado " + nome_estado + ":");

        estado.setNome(nome_estado).setSigla(sigla_estado);

        try {
            manager.getTransaction().begin();
            manager.persist(estado);
            manager.getTransaction().commit();
        } catch (Exception ex) {
            String error = """
                        Erro!
                        Não foi possível salvar os dados desta operação.
                                            
                        """ + ex.getMessage();
            System.err.println(error);
            JOptionPane.showMessageDialog(null, error);
        }
    }
}