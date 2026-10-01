package padroescomportamentais.state;

public final class ReprodutorEstadoParado extends ReprodutorEstado {
    private static final ReprodutorEstadoParado instance = new ReprodutorEstadoParado();

    private ReprodutorEstadoParado() {
    }

    public static ReprodutorEstadoParado getInstance() {
        return instance;
    }

    @Override
    public String getEstado() {
        return "Parado";
    }

    @Override
    public boolean reproduzir(Reprodutor reprodutor) {
        reprodutor.setEstado(ReprodutorEstadoReproduzindo.getInstance());
        return true;
    }
}
