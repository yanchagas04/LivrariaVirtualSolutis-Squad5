import javax.persistence.*;
import java.sql.SQLException;
import java.util.List;

public class RepositorioLivraria {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("livrariavirtualsquad5");
    EntityManager em = emf.createEntityManager();

    public void cadastrar(Object o){
        try {
            em.getTransaction().begin();
            em.persist(o);
            em.getTransaction().commit();
        } finally {

        }
    }

    public void fecharConexao(){
        emf.close();
    }

    public List<Impresso> pegarLivrosImpressos(){
        List<Impresso> impressos = null;
        try {
            impressos = em.createQuery("SELECT i FROM Impresso i", Impresso.class).getResultList();
        } catch (Exception e) {
            System.out.println("Erro no banco de dados");
        }
        return impressos;
    }

    public List<Eletronico> pegarLivrosEletronicos(){
        List<Eletronico> eletronicos = null;
        try {
            eletronicos = em.createQuery("SELECT e FROM Eletronico e", Eletronico.class).getResultList();
        } catch (Exception e){
            System.out.println("Erro no banco de dados");
        }
        return eletronicos;
    }

    public List<Venda> pegarVendas(){
        List<Venda> vendas = null;
        try {
            vendas = em.createQuery("SELECT v FROM Venda v", Venda.class).getResultList();
        } catch (Exception e){
            System.out.println("Erro no banco de banco de dados");
        }
        return vendas;
    }

    public void aumentarEstoqueImpresso(Impresso impresso){
        try {
            int id = -1;
            List<Impresso> listaImp = pegarLivrosImpressos();
            for (Impresso imp : listaImp){
                if (LivrariaVirtual.impressoIgual(imp, impresso))
                    id = imp.getId();
            }
            em.getTransaction().begin();
            Impresso i = em.find(Impresso.class, id);
            i.setEstoque(i.getEstoque() + 1);
            em.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Erro no banco de dados");
        }
    }

    public void diminuirEstoqueImpresso(Impresso impresso){
        try {
            int id = -1;
            List<Impresso> listaImp = pegarLivrosImpressos();
            for (Impresso imp : listaImp){
                if (LivrariaVirtual.impressoIgual(imp, impresso))
                    id = imp.getId();
            }
            em.getTransaction().begin();
            Impresso i = em.find(Impresso.class, id);
            if (i.getEstoque() > 0){
                i.setEstoque(i.getEstoque() - 1);
            }
            em.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Erro no banco de dados");
        }
    }
}
