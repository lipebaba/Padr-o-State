package padroescomportamentais.state;

public abstract class ReprodutorEstado {
    public abstract String getEstado();

    public boolean reproduzir(Reprodutor reprodutor) {
        return false;
    }

    public boolean pausar(Reprodutor reprodutor) {
        return false;
    }

    public boolean parar(Reprodutor reprodutor) {
        return false;
    }
}
