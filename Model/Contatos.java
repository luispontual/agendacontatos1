package Model;

public class Contatos {

    private int identificador;
    private String nomeContato;
    private String telefonePrincipal;
    private String emailPrincipal;

    public Contatos() {
    }

    public Contatos(int identificador, String nomeContato, String telefonePrincipal, String emailPrincipal) {
        this.identificador = identificador;
        this.nomeContato = nomeContato;
        this.telefonePrincipal = telefonePrincipal;
        this.emailPrincipal = emailPrincipal;
    }

    public int getId() {
        return identificador;
    }

    public void setId(int id) {
        this.identificador = id;
    }

    public String getNome() {
        return nomeContato;
    }

    public void setNome(String nome) {
        this.nomeContato = nome;
    }

    public String getTelefone() {
        return telefonePrincipal;
    }

    public void setTelefone(String telefone) {
        this.telefonePrincipal = telefone;
    }

    public String getEmail() {
        return emailPrincipal;
    }

    public void setEmail(String email) {
        this.emailPrincipal = email;
    }
}
