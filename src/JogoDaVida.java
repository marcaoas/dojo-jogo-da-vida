import java.io.Console;

public class JogoDaVida {
	
	private static final char VIVA = '*';
	private static final char MORTA = '.';
	
	private boolean [][] tabuleiro;
	private boolean [][] proximoRodada;
	private int linhas;
	private int colunas;
	
	public JogoDaVida(int linhas, int colunas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.tabuleiro = new boolean[linhas][colunas];
	}
	
	public JogoDaVida(String tabuleiro){
		if(tabuleiro.charAt(tabuleiro.length()-1)!='\n'){
			tabuleiro += "\n";
		}
		for(int i=0;i<tabuleiro.length();i++){
			if(tabuleiro.charAt(i)=='\n'){
				this.colunas = i;
				break;
			}
		}
		this.linhas = 0;
		for(int i=0;i<tabuleiro.length();i++){
			if(tabuleiro.charAt(i)=='\n'){
				this.linhas++;
			}
		}
		this.tabuleiro = new boolean[this.linhas][this.colunas];
		int x = 1, y=1;
		for(int i=0;i<tabuleiro.length();i++){
			if(tabuleiro.charAt(i)=='\n'){
				x++;
				y=0;
			}
			if(tabuleiro.charAt(i)==this.VIVA){
				avivar(x,y);
			}
			y++;
		}
	}
	
	public String getEstado(int x, int y){
		return this.getString(this.tabuleiro[x-1][y-1]);
	}
	
	public void avivar(int x, int y){
		this.tabuleiro[x-1][y-1] = true;
	}
	
	public void matar (int x, int y){
		this.tabuleiro[x-1][y-1] = false;
	}
	
	public int getQtdVizinhosVivos(int x, int y){
		int qtdVizinhosVivos = 0;
		boolean valorCasa = this.tabuleiro[x-1][y-1];
		for(int i=x-1;i<=x+1;i++){
			for(int j=y-1;j<=y+1;j++){
				if(i!=x || j!=y){
					if(coordenadaExiste(i, j)){
						if (this.tabuleiro[i-1][j-1]){
							qtdVizinhosVivos++;
						}
					}
				}
			}
		}
		return qtdVizinhosVivos;
	}
	
	private boolean coordenadaExiste(int x, int y){
		if(x<=0 || x>this.linhas){
			return false;
		}
		if(y<=0 || y>this.colunas){
			return false;
		}
		return true;
	}

	private String getString(boolean estado){
		if(estado){
			return String.valueOf(this.VIVA);
		} else {
			return String.valueOf(this.MORTA);
		}
	}
	
	public String toString(){
		String retorno = "";
		for(int i=1;i<=this.linhas;i++){
			for(int j=1;j<=this.colunas;j++){
				retorno += getEstado(i,j);
			}
			retorno += "\n";
		}
		return retorno;
	}

	public void executar() {
		this.proximoRodada = new boolean[this.linhas][this.colunas];
		for(int i=0;i<this.linhas;i++){
			for(int j=0;j<this.colunas;j++){
				int qtdVizinhos = getQtdVizinhosVivos(i+1, j+1);
				if(this.tabuleiro[i][j]){
					if(qtdVizinhos<2 || qtdVizinhos > 3){
						this.proximoRodada[i][j] = false;
					} else {
						this.proximoRodada[i][j] = true;
					}
				} else {
					if(qtdVizinhos==3){
						this.proximoRodada[i][j] = true;
					}
				}
			}
		}
		this.tabuleiro = this.proximoRodada;
		this.proximoRodada = null;
	}
	
	public static void main(String args[]){
		JogoDaVida jogo = new JogoDaVida(10,10);
		jogo.avivar(8, 8);
		jogo.avivar(8, 9);
		jogo.avivar(8, 10);
		jogo.avivar(9, 8);
		jogo.avivar(10, 9);
		for(int i=0;i<50;i++){
			System.out.print("\n\n");
			System.out.print(jogo.toString());
			jogo.executar();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
    
