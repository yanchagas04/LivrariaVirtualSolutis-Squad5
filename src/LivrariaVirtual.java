import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LivrariaVirtual {
    private final int MAX_IMPRESSOS = 20;
    private final int MAX_ELETRONICOS = 20;
    private final int MAX_VENDAS = 40;
    private Impresso[] impressos;
    private Eletronico[] eletronicos;
    private Venda[] vendas;
    private int numImpressos;
    private int numEletronicos;
    private int numVendas;
    private RepositorioLivraria repo = new RepositorioLivraria();

    public LivrariaVirtual() {
        this.impressos = new Impresso[MAX_IMPRESSOS];
        this.eletronicos = new Eletronico[MAX_ELETRONICOS];
        this.vendas = new Venda[MAX_VENDAS];
        this.numImpressos = 0;
        this.numEletronicos = 0;
        this.numVendas = 0;
    }

    public boolean eletronicoIgual(Eletronico e, Eletronico e2) {
        return e2.getTitulo().equals(e.getTitulo()) && e2.getAutores().equals(e.getAutores()) && e2.getPreco() == e.getPreco() && e2.getEditora().equals(e.getEditora()) && e2.getTamanho() == e.getTamanho();
    }

    public int eletronicoExistente(Eletronico e) {
        for (int j = 0; j < numEletronicos; j++) {
            if (eletronicoIgual(eletronicos[j], e)) {
                //System.out.println("Livro ja cadastrado!");
                return j;
            }
        }
        return -1;
    }

    public static boolean impressoIgual(Impresso i, Impresso i2) {
        return i2.getTitulo().equals(i.getTitulo()) && i2.getAutores().equals(i.getAutores()) && i2.getPreco() == i.getPreco() && i2.getEditora().equals(i.getEditora()) && i2.getFrete() == i.getFrete();
    }

    public int impressoExistente(Impresso i) {
        List<Impresso> impressosB = repo.pegarLivrosImpressos();
        for (int j = 0; j < impressosB.size(); j++) {
            if (impressoIgual(impressosB.get(j), i)) {
                //System.out.println("Livro ja cadastrado!");
                return j;
            }
        }
        return -1;
    }

    public boolean addImpresso(Impresso i) {
        int ie = impressoExistente(i);
        if (ie != -1){ // verifica se o impresso ja existe
            repo.aumentarEstoqueImpresso(i);
            //impressos[ie].setEstoque(impressos[ie].getEstoque() + 1); //aumenta o estoque desse livro
            System.out.println("\nLivro já cadastrado. Aumentando o estoque.\n");
            return true; // retorna true se o impresso ja existe, não adicionando um novo
        }
        else if (numImpressos < MAX_IMPRESSOS) {
            i.aumentarEstoque();
            impressos[numImpressos] = i;
            numImpressos++;
            repo.cadastrar(i);
            return true;
        }
        return false;
    }

    public boolean addEletronico(Eletronico e) {
        if (eletronicoExistente(e) != -1) // verifica se o eletronico ja existe
            return true; // retorna true se o eletronico ja existe, não adicionando um novo
        if (numEletronicos < MAX_ELETRONICOS) {
            eletronicos[numEletronicos] = e;
            numEletronicos++;
            repo.cadastrar(e);
            return true;
        }
        return false;
    }

    public boolean addAmbos(Impresso i, Eletronico e) {
        if (numImpressos < MAX_IMPRESSOS && numEletronicos < MAX_ELETRONICOS) {
            addEletronico(e);
            addImpresso(i);
            return true;
        }
        return false;
    }

    public boolean addVenda(Venda v) {
        if (numVendas < MAX_VENDAS) {
            vendas[numVendas] = v;
            numVendas++;
            repo.cadastrar(v);
            return true;
        }
        return false;
    }

    public int impressosEmEstoque(){
        List<Impresso> imp = repo.pegarLivrosImpressos();
        int total = 0;
        if (!imp.isEmpty()){
            for (Impresso i : imp) {
                if (i != null) {
                    total += i.getEstoque();
                }
            }
        }
        return total;
    }

    public void cadastrarLivro(Scanner sc){
        sc = new Scanner(System.in);
        int tipo = 0;
        limparConsole();
        while (tipo != 1 && tipo != 2 && tipo != 3) {
            try {
                System.out.println("\nDigite o tipo de livro que deseja cadastrar:");
                System.out.println("1. Impresso");
                System.out.println("2. Eletrônico");
                System.out.println("3. Ambos");
                System.out.print("\n-> ");
                tipo = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                limparConsole();
                System.out.println("Entrada inválida. Tente novamente.");
                sc.nextLine();
            }
        }
        if (tipo == 1 && numImpressos == MAX_IMPRESSOS) {
            limparConsole();
            System.out.println("Limite de livros impressos atingido. Impossível adicionar mais livros.");
            return;
        } else if (tipo == 2 && numEletronicos == MAX_ELETRONICOS) {
            limparConsole();
            System.out.println("Limite de livros eletronicos atingido. Impossível adicionar mais livros.");
            return;
        } else if (tipo == 3 && numImpressos == MAX_IMPRESSOS && numEletronicos == MAX_ELETRONICOS){
            limparConsole();
            System.out.println("Limite de livros impressos e eletronicos atingido. Impossível adicionar mais livros.");
            return;
        } else if (tipo == 3 && numImpressos == MAX_IMPRESSOS){
            limparConsole();
            System.out.println("Limite de livros impressos atingido. Impossível adicionar mais livros.");
            return;
        } else if (tipo == 3 && numEletronicos == MAX_ELETRONICOS){
            limparConsole();
            System.out.println("Limite de livros eletronicos atingido. Impossível adicionar mais livros.");
            return;
        }

        String titulo, autor, editora;
        float preco;
        sc = new Scanner(System.in);
        limparConsole();
        while (true){
            try {
                System.out.println("\nDigite o titulo do livro: ");
                titulo = sc.nextLine();
                System.out.println("\nDigite o(s) autor(es) do livro: ");
                autor = sc.nextLine();
                System.out.println("\nDigite a editora do livro: ");
                editora = sc.nextLine();
                System.out.println("\nDigite o preço do livro: ");
                preco = sc.nextFloat();
                break;
            } catch (InputMismatchException e) {
                limparConsole();
                System.out.println("Entrada inválida. Tente novamente.");
                limparScanner(sc);
            }
        }
        
        float frete;
        int tamanho;
        limparScanner(sc);
        while (true){
            try {
                if (tipo == 1) {
                    System.out.println("\nDigite o frete cobrado para entrega do livro:");
                    frete = sc.nextFloat();
                    addImpresso(new Impresso(titulo, autor, editora, preco, frete));
                    limparConsole();
                    break;
                } else if (tipo == 2) {
                    System.out.println("\nDigite o tamanho do arquivo (em KB):");
                    tamanho = sc.nextInt();
                    addEletronico(new Eletronico(titulo, autor, editora, preco, tamanho));
                    limparConsole();
                    break;
                } else if (tipo == 3) {
                    System.out.println("\nDigite o frete cobrado para entrega do livro:");
                    frete = sc.nextFloat();
                    System.out.println("\nDigite o tamanho do arquivo (em KB):");
                    tamanho = sc.nextInt();
                    addImpresso(new Impresso(titulo, autor, editora, preco, frete));
                    addEletronico(new Eletronico(titulo, autor, editora, preco, tamanho));
                    limparConsole();
                    break;
                } else {
                    System.out.println("Opcão inválida. Tente novamente.\n");
                }
            } catch (InputMismatchException e) {
                limparConsole();
                System.out.println("Entrada inválida. Tente novamente.");
                sc.nextLine();
            }
        }
        System.out.println("\nLivro cadastrado com sucesso!\n");
        
    }

    public float calcularValorTotal(Livro[] livros){
        float total = 0;
        for (Livro l : livros){
            if (l != null){
                total += l.getPreco();
                if (l instanceof Impresso)
                    total += ((Impresso) l).getFrete();
            }
        }
        return total;
    }

    public void realizarVenda(Scanner sc){
        sc = new Scanner(System.in);
        if (repo.pegarVendas().size() == MAX_VENDAS){
            limparConsole();
            System.out.println("Limite de vendas atingido. Impossível realizar mais vendas.");
            return;
        }
        if (impressosEmEstoque() + repo.pegarLivrosEletronicos().size() == 0){
            limparConsole();
            System.out.println("\nNenhum livro no estoque e/ou cadastrado. Impossível realizar vendas.\n");
            return;
        }
        limparConsole();
        System.out.println("\nDigite o nome do cliente: ");
        String nomeCliente = sc.nextLine();
        int numLivros;
        while (true) { 
            System.out.println("\nDigite a quantidade de livros: ");
            numLivros = sc.nextInt();
            if (numLivros <= 0)
                System.out.println("Quantidade de livros inválida. Tente novamente.\n");
            else if (numLivros > impressosEmEstoque() && repo.pegarLivrosEletronicos().isEmpty())
                System.out.println("Não há essa quantidade de livros no estoque nem livros eletrônicos cadastrados. Tente novamente.\n");
            else
                break;
        }
        Venda v = new Venda();
        int indexAtual = 0;
        for (int i = 0; i < numLivros; i++){
            limparConsole();
            System.out.println("\nDigite o tipo de livro:");
            System.out.println("1. Impresso");
            System.out.println("2. Eletrônico");
            System.out.print("\n-> ");
            int tipo = 0;
            while (tipo != 1 && tipo != 2) {
                tipo = sc.nextInt();
                if (tipo != 1 && tipo != 2){
                    limparConsole();
                    System.out.println("Opcão inválida. Tente novamente.\n");
                }
            }
            if (tipo == 1 && repo.pegarLivrosImpressos().isEmpty()) {
                System.out.println("Nenhum livro impresso cadastrado.");
                return;
            }
            else if (tipo == 2 && repo.pegarLivrosEletronicos().isEmpty()) {
                System.out.println("Nenhum livro eletrônico cadastrado.");
                return;
            } else if (tipo == 1 && impressosEmEstoque() == 0) {
                System.out.println("Nenhum livro em estoque.");
                return;
            }
            limparConsole();
            if (tipo == 1){
                listarLivrosImpressos();
            } else if (tipo == 2) {
                listarLivrosEletronicos();
            }
            int indice = 0;
            List<Impresso> impressosB = repo.pegarLivrosImpressos();
            List<Eletronico> eletronicosB = repo.pegarLivrosEletronicos();
            while (true) {
                System.out.println("Qual livro gostaria de comprar?");
                System.out.print("-> ");
                indice = sc.nextInt();
                try {
                    if (tipo == 1 && impressosB.get(indice - 1).getEstoque() == 0){
                        limparConsole();
                        System.out.println("Livro fora de estoque. Tente novamente.\n");
                    } else
                        break;
                } catch (ArrayIndexOutOfBoundsException e) {
                    limparConsole();
                    System.out.println("Opcão inválida. Tente novamente.\n");
                    limparScanner(sc);
                }
            }
            v.setCliente(nomeCliente);
            if (tipo == 1){
                v.addLivro(impressosB.get(indice - 1), indexAtual);
                impressosB.get(indice - 1).diminuirEstoque();
            } else if (tipo == 2) {
                v.addLivro(eletronicosB.get(indice - 1), indexAtual);
            }
            indexAtual++;
        }
        v.setNumero(repo.pegarVendas().size());
        addVenda(v);
        limparConsole();
        System.out.println("Venda realizada com sucesso!\n");
    }

    public void listarLivrosEletronicos(){
        List<Eletronico> eletronicosBanco = repo.pegarLivrosEletronicos();
        System.out.println("\nLIVROS ELETRÔNICOS CADASTRADOS:\n");
        if (eletronicosBanco.isEmpty()) {
            System.out.println("\tNenhum livro eletrônico cadastrado.\n");
        }
        for (int i = 0; i < eletronicosBanco.size(); i++) {
            System.out.println((i + 1) + ". {");
            System.out.println(eletronicosBanco.get(i));
            System.out.println("}\n");
        }
    }

    public void listarLivrosImpressos(){
        List<Impresso> impressosBanco = repo.pegarLivrosImpressos();
        System.out.println("\nLIVROS IMPRESSOS CADASTRADOS:\n");
        if (impressosBanco.isEmpty()) {
            System.out.println("\tNenhum livro impresso cadastrado.\n");
        }
        for (int i = 0; i < impressosBanco.size(); i++) {
            System.out.println((i + 1) + ". {");
            System.out.println(impressosBanco.get(i));
            System.out.println("}\n");
        }
    }

    public void listarLivros(){
        limparConsole();
        listarLivrosImpressos();
        listarLivrosEletronicos();
    }

    public void listarVendas(){
        List<Venda> vendasBanco = repo.pegarVendas();
        limparConsole();
        System.out.println("\nVENDAS CADASTRADAS:\n");
        if (vendasBanco.isEmpty()) {
            System.out.println("\tNenhuma venda cadastrada.\n");
        }
        for (int i = 0; i < vendasBanco.size(); i++) {
            System.out.println((i + 1) + ". {");
            System.out.println(vendasBanco.get(i));
            System.out.println("}\n");
        }
    }

    public int getMAX_IMPRESSOS() {
        return MAX_IMPRESSOS;
    }

    public int getMAX_ELETRONICOS() {
        return MAX_ELETRONICOS;
    }

    public int getMAX_VENDAS() {
        return MAX_VENDAS;
    }

    public Impresso[] getImpressos() {
        return impressos;
    }

    public void setImpressos(Impresso[] impressos) {
        this.impressos = impressos;
    }

    public Eletronico[] getEletronicos() {
        return eletronicos;
    }

    public void setEletronicos(Eletronico[] eletronicos) {
        this.eletronicos = eletronicos;
    }

    public Venda[] getVendas() {
        return vendas;
    }

    public void setVendas(Venda[] vendas) {
        this.vendas = vendas;
    }

    public int getNumImpressos() {
        return numImpressos;
    }

    public void setNumImpressos(int numImpressos) {
        this.numImpressos = numImpressos;
    }

    public int getNumEletronicos() {
        return numEletronicos;
    }

    public void setNumEletronicos(int numEletronicos) {
        this.numEletronicos = numEletronicos;
    }

    public int getNumVendas() {
        return numVendas;
    }

    public void setNumVendas(int numVendas) {
        this.numVendas = numVendas;
    }

    public static void limparScanner(Scanner sc) {
        if (sc.hasNextLine()){
            sc.nextLine();
        }
    }

    public static void limparConsole(){
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
    }

    public static void main(String[] args) {
        LivrariaVirtual livraria = new LivrariaVirtual();
        Scanner sc = new Scanner(System.in);
        int op = 0;
        while (true) {
            try {
                System.out.println("Livraria Virtual:\n");
                System.out.println("\t1. Cadastrar livros");
                System.out.println("\t2. Realizar uma venda");
                System.out.println("\t3. Listar livros");
                System.out.println("\t4. Listar vendas");
                System.out.println("\t5. Sair");
                System.out.print("\n-> ");
                op = sc.nextInt();
                sc.nextLine();
                if (op == 1) {
                    livraria.cadastrarLivro(sc);
                } else if (op == 2) {
                    livraria.realizarVenda(sc);
                } else if (op == 3) {
                    livraria.listarLivros();
                } else if (op == 4) {
                    livraria.listarVendas();
                } else if (op == 5) {
                    livraria.repo.fecharConexao();
                    break;
                } else {
                    limparConsole();
                    System.out.println("Opcão inválida. Tente novamente.\n");
                }
            } catch (InputMismatchException e) {
                limparConsole();
                System.out.println("Entrada inválida. Tente novamente.\n");
                sc.nextLine();
            }
        }
        sc.close();
    }
}