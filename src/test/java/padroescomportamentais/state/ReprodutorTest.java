package padroescomportamentais.state;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import java.util.function.Function;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class ReprodutorTest {
    @Test
    void deveIniciarParado() {
        Reprodutor reprodutor = new Reprodutor();
        assertEquals("Parado", reprodutor.getNomeEstado());
        assertSame(ReprodutorEstadoParado.getInstance(), reprodutor.getEstado());
    }

    @TestFactory
    Stream<DynamicTest> deveRespeitarTodasAsTransicoes() {
        return Stream.of(
            caso("Parado", "reproduzir", Reprodutor::reproduzir, true, "Reproduzindo"),
            caso("Parado", "pausar", Reprodutor::pausar, false, "Parado"),
            caso("Parado", "parar", Reprodutor::parar, false, "Parado"),
            caso("Reproduzindo", "reproduzir", Reprodutor::reproduzir, false, "Reproduzindo"),
            caso("Reproduzindo", "pausar", Reprodutor::pausar, true, "Pausado"),
            caso("Reproduzindo", "parar", Reprodutor::parar, true, "Parado"),
            caso("Pausado", "reproduzir", Reprodutor::reproduzir, true, "Reproduzindo"),
            caso("Pausado", "pausar", Reprodutor::pausar, false, "Pausado"),
            caso("Pausado", "parar", Reprodutor::parar, true, "Parado"));
    }

    private DynamicTest caso(String inicial, String comando,
            Function<Reprodutor, Boolean> acao, boolean aceito, String esperado) {
        return DynamicTest.dynamicTest(inicial + " / " + comando, () -> {
            Reprodutor reprodutor = preparar(inicial);
            ReprodutorEstado anterior = reprodutor.getEstado();
            assertEquals(aceito, acao.apply(reprodutor).booleanValue());
            assertEquals(esperado, reprodutor.getNomeEstado());
            if (!aceito) {
                assertSame(anterior, reprodutor.getEstado());
            } else {
                assertNotSame(anterior, reprodutor.getEstado());
            }
        });
    }

    private Reprodutor preparar(String estado) {
        Reprodutor reprodutor = new Reprodutor();
        if ("Reproduzindo".equals(estado) || "Pausado".equals(estado)) {
            assertTrue(reprodutor.reproduzir());
        }
        if ("Pausado".equals(estado)) {
            assertTrue(reprodutor.pausar());
        }
        assertEquals(estado, reprodutor.getNomeEstado());
        return reprodutor;
    }

    @Test
    void deveCompletarCicloDeReproducao() {
        Reprodutor reprodutor = new Reprodutor();
        assertTrue(reprodutor.reproduzir());
        assertEquals("Reproduzindo", reprodutor.getNomeEstado());
        assertTrue(reprodutor.pausar());
        assertEquals("Pausado", reprodutor.getNomeEstado());
        assertTrue(reprodutor.reproduzir());
        assertEquals("Reproduzindo", reprodutor.getNomeEstado());
        assertTrue(reprodutor.parar());
        assertEquals("Parado", reprodutor.getNomeEstado());
    }

    @Test
    void deveManterReprodutoresIndependentesComEstadosCompartilhados() {
        Reprodutor primeiro = new Reprodutor();
        Reprodutor segundo = new Reprodutor();
        assertSame(primeiro.getEstado(), segundo.getEstado());
        assertTrue(primeiro.reproduzir());
        assertEquals("Parado", segundo.getNomeEstado());
        assertTrue(segundo.reproduzir());
        assertSame(primeiro.getEstado(), segundo.getEstado());
        assertTrue(primeiro.pausar());
        assertEquals("Pausado", primeiro.getNomeEstado());
        assertEquals("Reproduzindo", segundo.getNomeEstado());
    }

    @Test
    void deveRejeitarEstadoNuloSemAlterarEstadoAtual() {
        Reprodutor reprodutor = new Reprodutor();
        ReprodutorEstado anterior = reprodutor.getEstado();
        assertThrows(NullPointerException.class, () -> reprodutor.setEstado(null));
        assertSame(anterior, reprodutor.getEstado());
    }
}
