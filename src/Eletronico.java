import javax.persistence.*;

@Entity
public class Eletronico extends Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tamanho", nullable = false, length = 45, unique = false)
    private int tamanho;

    public Eletronico(){

    }

    public Eletronico(String titulo, String autores, String editora, float preco, int tamanho) {
        super(titulo, autores, editora, preco);
        this.tamanho = tamanho;
    }

    @Override
    public String toString() {
        return super.toString() + "\n\tTamanho do arquivo: " + (tamanho > 0 ? tamanho + " KB" : "não informado");
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
}