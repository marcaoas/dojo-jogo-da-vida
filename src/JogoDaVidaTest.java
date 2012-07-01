import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;


public class JogoDaVidaTest extends TestCase {
	
	public void testDeveRetornarOTabuleiroComTodasAsCasasMortas(){
		String esperado = "....\n....\n....\n....\n";
		JogoDaVida jogo = new JogoDaVida(4, 4);
		assertEquals(esperado, jogo.toString());
	}
	
	public void testDeveRetornarEstadoDoTabuleiroComCasasVivas(){
		JogoDaVida jogo = new JogoDaVida(4, 4);
		jogo.avivar(1, 2);
		jogo.avivar(1, 3);
		jogo.avivar(2, 1);
		jogo.avivar(2, 4);
		jogo.avivar(3, 1);
		jogo.avivar(3, 2);
		jogo.avivar(3, 3);
		jogo.avivar(3, 4);
		String esperado = ".**.\n*..*\n****\n....\n";
		assertEquals(esperado, jogo.toString());
	}
	
	public void testRecuperaEstadoInicialDe0x0() {
		JogoDaVida jogo = new JogoDaVida(1, 1);
		assertEquals(".",jogo.getEstado(1, 1));
	}
	
	public void testRecuperaEstadoDe0x0DepoisDeAvivar() {
		JogoDaVida jogo = new JogoDaVida(1, 1);
		jogo.avivar(1, 1);
		assertEquals("*", jogo.getEstado(1,1));
	}
	
	public void testRecuperaEstadoDe0x0DepoisDeMatar(){
		JogoDaVida jogo = new JogoDaVida(1, 1);
		jogo.avivar(1, 1);
		jogo.matar(1, 1);
		assertEquals(".",jogo.getEstado(1, 1));
	}
	
	public void testLancaExcecaoAoRetornarCelulaForaDoTabuleiro(){
		JogoDaVida jogo = new JogoDaVida(1, 1);
		try {
			jogo.getEstado(2, 2);
			fail("Excessão esperada não lançada!");
		} catch (Exception ex) {
			assertTrue(true);
		}
	}
	
	public void testRetorna0VizinhosVivos(){
		JogoDaVida jogo = new JogoDaVida(3, 3);
		assertEquals(0, jogo.getQtdVizinhosVivos(2,2));
	}
	
	public void testRetorna1VizinhoVivo(){
		JogoDaVida jogo = new JogoDaVida(3, 3);
		jogo.avivar(1, 1);
		assertEquals(1, jogo.getQtdVizinhosVivos(2, 2));
	}
	
	public void testRetorna8VizinhosVivos(){
		JogoDaVida jogo = new JogoDaVida(3, 3);
		jogo.avivar(1, 1);
		jogo.avivar(1, 2);
		jogo.avivar(1, 3);
		jogo.avivar(2, 1);
		jogo.avivar(2, 3);
		jogo.avivar(3, 1);
		jogo.avivar(3, 2);
		jogo.avivar(3, 3);
		assertEquals(8, jogo.getQtdVizinhosVivos(2, 2));
	}
	
	public void testRetorna0VizinhosVivosDeUmaCasaViva(){
		JogoDaVida jogo = new JogoDaVida(3, 3);
		jogo.avivar(2, 2);
		assertEquals(0, jogo.getQtdVizinhosVivos(2, 2));
	}
	
	public void testRetorna0VizinhosVivosDeUmaCasaNoCanto(){
		JogoDaVida jogo = new JogoDaVida(2, 2);
		assertEquals(0, jogo.getQtdVizinhosVivos(1, 1));
	}
	
	public void testRetorna1VizinhosVivosDeUmaCasaNoCanto(){
		JogoDaVida jogo = new JogoDaVida(2, 2);
		jogo.avivar(2, 2);
		assertEquals(1, jogo.getQtdVizinhosVivos(1, 1));
	}
	
	public void testDeveLancarExcessaoCasaForaDoTbuleiro(){
		JogoDaVida jogo = new JogoDaVida(2, 2);
		try{
			assertEquals(1, jogo.getQtdVizinhosVivos(3, 1));
			fail("Excessão esperada não lançada!");
		} catch(Exception e ){
			assertTrue(true);
		}
	}
	
	public void testDeveCriarOTabuleiroAPartirDeUmaString(){
		String tabuleiro = "..\n..\n";
		JogoDaVida jogo = new JogoDaVida(tabuleiro);
		assertEquals(tabuleiro, jogo.toString());
	}
	
	public void testDeveCriarOTabuleiroNãoQuadradoAPartirDeUmaString(){
		String tabuleiro = "....\n....\n";
		JogoDaVida jogo = new JogoDaVida(tabuleiro);
		assertEquals(tabuleiro, jogo.toString());
	}
	
	public void testDeveCriarOTabuleiroEAvivarUmaCasaAPartirDeUmaString(){
		String tabuleiro = ".*\n.*\n";
		JogoDaVida jogo = new JogoDaVida(tabuleiro);
		assertEquals(tabuleiro, jogo.toString());
	}
	
	public void testCelulaComMenosDeDoisVizinhosVivosDeveMorrer(){
		JogoDaVida jogo = new JogoDaVida("*.\n..\n");
		jogo.executar();
		assertEquals(".", jogo.getEstado(1, 1));
	}
	
	public void testCelulaVivaComMaisDeTresVizinhosDeveMorrer(){
		JogoDaVida jogo = new JogoDaVida(".*.\n**.\n**.");
		jogo.executar();
		assertEquals(".", jogo.getEstado(2, 1));
		assertEquals(".", jogo.getEstado(2, 2));
	}
	
	public void testCelulaMortaCom3VizinhosVivosDeveViver(){
		JogoDaVida jogo = new JogoDaVida(".*.\n**.\n...");
		jogo.executar();
		assertEquals("*",jogo.getEstado(1, 1));
	}
	
	public void testCelulaVivaCom3Ou2VizinhosVivosDeveContinuarViva(){
		JogoDaVida jogo = new JogoDaVida(".*.\n*.*\n.*.");
		jogo.executar();
		assertEquals(".*.\n*.*\n.*.\n",jogo.toString());
	}
	
	public void testPadraoFigura(){
		JogoDaVida jogo = new JogoDaVida("...\n***\n...");
		jogo.executar();
		assertEquals(".*.\n.*.\n.*.\n",jogo.toString());
		jogo.executar();
		assertEquals("...\n***\n...\n",jogo.toString());
		jogo.executar();
		assertEquals(".*.\n.*.\n.*.\n",jogo.toString());
	}
}
