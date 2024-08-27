import javax.persistence.*;

@Entity
public class Venda {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "venda_livro", joinColumns = @JoinColumn(name = "venda_id"), inverseJoinColumns = @JoinColumn(name = "livro_id"))
    @OrderColumn
    private Livro[] livros = new Livro[0];

    private static int numVendas = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "numero", nullable = false, unique = false)
    private int numero;

    @Column(name = "cliente", nullable = false, unique = false)
    private String cliente;

    @Column(name = "valor")
    private float valor;

    //construtoress

    public Venda() {
        this.valor = 0f;
    }

    public Venda(String cliente, Livro[] livros, float valor) {
        this.cliente = cliente;
        this.valor = valor;
    }

    //metodos
    public void addLivro(Livro l, int index) {
        if (index < 0 || index > livros.length) {
            throw new IndexOutOfBoundsException("Índice inválido");
        }

        Livro[] novosLivros = new Livro[livros.length + 1];
        System.arraycopy(livros, 0, novosLivros, 0, index);
        novosLivros[index] = l;
        System.arraycopy(livros, index, novosLivros, index + 1, livros.length - index);
        this.livros = novosLivros;
        this.valor += l.getPreco();
        if (l instanceof Impresso){
            this.valor += ((Impresso) l).getFrete();
        }
    }

    public void listarLivros() {
        for (Livro livro : livros) {
            System.out.println(livro.toString());
        }
    }

    //get e set
    public int getNumVendas() {
        return numVendas;
    }

    public void setNumVendas(int num) {
        numVendas = num;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        String stringLivros = "";

        for (Livro livro : livros) {
            stringLivros += "\n\t\t- " + livro.getTitulo() + " por " + livro.getAutores();
        }
        return "\tLivros:" + stringLivros + "\n\tNúmero=" + numero + "\n\tCliente=" + cliente + "\n\tValor: R$"
                + valor;
    }

}
