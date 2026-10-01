package padroescomportamentais.state;

public class Aplicacao {
    public static void main(String[] args) {
        Reprodutor reprodutor = new Reprodutor();
        System.out.println("Estado inicial: " + reprodutor.getNomeEstado());
        mostrar("Pausar", reprodutor.pausar(), reprodutor);
        mostrar("Reproduzir", reprodutor.reproduzir(), reprodutor);
        mostrar("Pausar", reprodutor.pausar(), reprodutor);
        mostrar("Retomar", reprodutor.reproduzir(), reprodutor);
        mostrar("Parar", reprodutor.parar(), reprodutor);
    }

    private static void mostrar(String comando, boolean aceito, Reprodutor reprodutor) {
        System.out.printf("%s: %s | Estado: %s%n", comando,
                aceito ? "aceito" : "recusado", reprodutor.getNomeEstado());
    }
}
