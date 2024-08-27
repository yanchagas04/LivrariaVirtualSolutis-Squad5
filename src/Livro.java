import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "titulo", nullable = false, unique = false)
    private String titulo;

    @Column(name = "autores", nullable = false, unique = false)
    private String autores;

    @Column(name = "editora", nullable = false, unique = false)
    private String editora;

    @Column(name = "preco", nullable = false, unique = false)
    private float preco;

    //construtores
    public Livro(){
        this.titulo = "não informado";
        this.autores = "não informado";
        this.editora = "não informado";
        this.preco = -1.0f;
    }

    public Livro(String titulo){
        this();
        this.titulo = titulo;
    }

    public Livro(String titulo, float preco){
        this(titulo);
        this.preco = preco;
    }

    public Livro(String titulo, String autores, float preco){
        this(titulo, preco);
        this.autores = autores;
    }

    public Livro(String titulo, String autores, String editora, float preco){
        this(titulo, autores, preco);
        this.editora = editora;
    }

    //métodos set
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    //métodos get
    public String getTitulo() {
        return titulo;
    }

    public String getAutores() {
        return autores;
    }

    public float getPreco() {
        return preco;
    }

    public String getEditora() {
        return editora;
    }

    //método toString

    @Override
    public String toString(){
        //Caso haja apenas um autor;
        String autores = "\tAutor: " +  getAutores() + "\n";

        //Caso haja mais de um autor (Autores separados com "," ou ";")
        if(getAutores().split(",").length > 1 || getAutores().split(";").length > 1){
            autores = "\tAutores: " + getAutores() + "\n";
        }

        String preco = "R$" + String.valueOf(getPreco());

        //Caso o preço não tenha sido informado
        if(getPreco() < 0){
            preco = "\t Preço não informado";
        }

        StringBuilder str = new StringBuilder("\tTitulo: " + getTitulo() + "\n");
        str.append(autores).append("\tEditora: ").append(getEditora()).append("\n\tPreço: ").append(preco);

        return str.toString();
    }

}
