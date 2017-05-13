import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;



public class Boggle {


	public static MyTrie     lex;	// The dictionary, stored in a Trie
	public Square[][] board;	// The 4x4 board
	public static MyTrie     foundWords;  // The dictionary words on the current board
	public MyTrie     guesses;	// The valid words made so far by our one player
	public String[]   dice;	// An array of dice -- explained later!
	public Stack<Square> squarez = new Stack<Square>();
	private int indexCount;

	public Boggle(String str) {
		dice = new String[16];
		board = new Square[4][4];
		lex = new MyTrie();
		foundWords = new MyTrie();
		guesses = new MyTrie();
		try{
			File small = new File(str);
			Scanner scanz = new Scanner(small);
			while (scanz.hasNext()){
				String temp = scanz.next();
				lex.add(temp);
			}
		}
		catch (FileNotFoundException e){
			System.out.print("problem with dictionary file");
		}

	}

	public Square[][] getBoard(){
		return board;
	}

	public int numGuesses(){
		return guesses.size();
	}

	public String toString(){
		int count = 0;
		String str = "";
		String eol = System.getProperty("line.separator");

		for (int i=0; i<4; i++){
			for (int j=0; j<4; j++){
				if (count>3){
					count=0;
					str = str + eol;
				}
				str = str + "  " + board[i][j].toString();
				count++;
			}
		}
		return str;
	}

	public boolean contains(String str){
		return foundWords.contains(str);
	}

	public boolean addGuess(String str){
				for (int i =0; i<4; i++){
					for (int j=0; j<4; j++){
						board[i][j].unmark();
					}
				}
		if (foundWords.contains(str)){
			if (!guesses.contains(str)){
				guesses.add(str);
				return true;
			}
		}
		return false;
	}

	public void newGame(){
		fillDice();
		fillBoardFromDice();
		foundWords = new MyTrie();
		guesses= new MyTrie();
		fillFoundWords();
		squarez= new Stack<Square>();
	}

	public ArrayList<Square> squaresForWord(String w){
		ArrayList<Square> lolz = new ArrayList<Square>();
		if (w.length()==0){
			return lolz;
		}
		int count =0;
		for (int i =0; i<4; i++){
			for (int j=0; j<4; j++){
				lolz.addAll(squaresForWord(board[i][j], w));
				board[i][j].unmark();
				squarez = new Stack<Square>();
			}
		}
		ArrayList<Square> lol = new ArrayList<Square>();
		for (int m=0; m<w.length(); m++){
			if (lolz.size()>0){
			lol.add(lolz.remove(0));
			}
		}
		return lol;
	}

	private void fillDice(){
		try{
			File dicez = new File("dice.txt");
			Scanner scanz = new Scanner(dicez);
			int count = 0;
			while (scanz.hasNextLine()){
				String temp = scanz.nextLine();
				dice[count]=temp;
				count++;
			}
		}
		catch (FileNotFoundException e) {
			System.out.print("file not found");
			System.exit(1);
		}
	}

	private void fillBoardFromDice(){
		ArrayList<Integer> dices = new ArrayList<Integer>();
		ArrayList<Integer> letters = new ArrayList<Integer>();
		for (int i=0; i<16; i++){
			if (i<6){
				letters.add(i);
			}
			dices.add(i);
		}
		for (int j=0; j<4; j++){
			for (int k=0; k<4; k++){
				int temp = (int) (dices.size()*Math.random());
				int h =dices.remove(temp);
				int y = (int) (6*Math.random());
				char c = dice[h].charAt(y);
				String cstr = "" + c;
				if (cstr.equals("q")){
					Square sq = new Square(j,k,"qu");
					board[j][k]= sq;
				}
				else{
					Square sq = new Square(j,k,cstr);
					board[j][k]=sq;
				}
			}
		}
	}

	private void search(Square sq, String prefix){

		sq.mark();

		if(lex.contains(prefix))
		{
			if (!foundWords.contains(prefix))
				foundWords.add(prefix);
		}

		if(lex.containsPrefix(prefix))
		{
			String l = sq.toString();
			if (sq.toString()=="q"){
				l= "qu";
			}


			if (sq.getY()!=3){
				if (!board[sq.getX()][sq.getY()+1].isMarked()){
					search(board[sq.getX()][sq.getY()+1], prefix+l);
				}
			}

			if (sq.getY()!=0 && sq.getX()!=0){
				if (!board[sq.getX()-1][sq.getY()-1].isMarked()){
					search(board[sq.getX()-1][sq.getY()-1], prefix+l);
				}
			}

			if (sq.getY()!=0 && sq.getX()!=3){
				if (!board[sq.getX()+1][sq.getY()-1].isMarked()){
					search(board[sq.getX()+1][sq.getY()-1], prefix+l);
				}
			}

			if (sq.getY()!=3 && sq.getX()!=0){
				if (!board[sq.getX()-1][sq.getY()+1].isMarked()){
					search(board[sq.getX()-1][sq.getY()+1], prefix+l);
				}
			}

			if (sq.getY()!=3 && sq.getX()!=3){
				if (!board[sq.getX()+1][sq.getY()+1].isMarked()){
					search(board[sq.getX()+1][sq.getY()+1], prefix+l);
				}
			}

			if (sq.getY()!=0){
				if (!board[sq.getX()][sq.getY()-1].isMarked()){
					search(board[sq.getX()][sq.getY()-1], prefix+l);
				}
			}

			if (sq.getX()!=3){
				if (!board[sq.getX()+1][sq.getY()].isMarked()){
					search(board[sq.getX()+1][sq.getY()], prefix+l);
				}
			}

			if (sq.getX()!=0){
				if (!board[sq.getX()-1][sq.getY()].isMarked()){
					search(board[sq.getX()-1][sq.getY()], prefix+l);
				}
			}
		}


		sq.unmark();
	}

	private void fillFoundWords(){

		//depends on ur implementation of search i think give me ur thoughts
		//I think this is pretty much good. It runs through the board and searches.

		for (int i =0; i<4; i++){
			for (int j=0; j<4; j++){
				search(board[i][j], "");
			}
		}

	}

	private ArrayList<Square> squaresForWord(Square sq, String w){

		sq.mark();
		squarez.push(sq);

		if (sq.toString().equals(w.substring(0, 1)))
		{
			if (w.charAt(0)=='q'){
				w = w.substring(2);
			}
			else{
				w = w.substring(1);
			}

			if (w.length() == 0)
			{
				ArrayList<Square> reversedSquareSequence = new ArrayList<Square>();
				ArrayList<Square> squareSequence = new ArrayList<Square>();

				for (Square s : squarez){
					reversedSquareSequence.add(s);
				}

				for (Square s : reversedSquareSequence){
					squareSequence.add(0, s);
				}
					
					return squareSequence;
			}

			if (sq.getY()!=3)
			{
				if (!board[sq.getX()][sq.getY()+1].isMarked()){
					ArrayList<Square> potential = squaresForWord(board[sq.getX()][sq.getY()+1], w);

					if (!potential.equals(new ArrayList<Square>()))
						return potential;
				}
			}

			if (sq.getY()!=0 && sq.getX()!=0)
			{
				if (!board[sq.getX()-1][sq.getY()-1].isMarked()){
					ArrayList<Square> potential = squaresForWord(board[sq.getX()-1][sq.getY()-1], w);

					if (!potential.equals(new ArrayList<Square>()))
						return potential;
				}
			}

			if (sq.getY()!=3 && sq.getX()!=3)
			{
				if (!board[sq.getX()+1][sq.getY()+1].isMarked()){
					ArrayList<Square> potential = squaresForWord(board[sq.getX()+1][sq.getY()+1], w);

					if (!potential.equals(new ArrayList<Square>()))
						return potential;
				}
			}

			if (sq.getY()!=0 && sq.getX()!=3)
			{
				if (!board[sq.getX()+1][sq.getY()-1].isMarked()){
					ArrayList<Square> potential = squaresForWord(board[sq.getX()+1][sq.getY()-1], w);

					if (!potential.equals(new ArrayList<Square>()))
						return potential;
				}
			}

			if (sq.getY()!=3 && sq.getX()!=0)
			{
				if (!board[sq.getX()-1][sq.getY()+1].isMarked()){
					ArrayList<Square> potential = squaresForWord(board[sq.getX()-1][sq.getY()+1], w);

					if (!potential.equals(new ArrayList<Square>()))
						return potential;
				}
			}

			if (sq.getY()!=0)
			{
				if (!board[sq.getX()][sq.getY()-1].isMarked()){

					ArrayList<Square> potential = squaresForWord(board[sq.getX()][sq.getY()-1], w);

					if (!potential.equals(new ArrayList<Square>()))
						return potential;
				}
			}
			if (sq.getX()!=3)
			{
				if (!board[sq.getX()+1][sq.getY()].isMarked() )	{
					ArrayList<Square> potential = squaresForWord(board[sq.getX()+1][sq.getY()], w);

					if (!potential.equals(new ArrayList<Square>()))
						return potential;
				}
			}
			if (sq.getX()!=0)
			{
				if (!board[sq.getX()-1][sq.getY()].isMarked() ){
					ArrayList<Square> potential = squaresForWord(board[sq.getX()-1][sq.getY()], w);

					if (!potential.equals(new ArrayList<Square>()))
						return potential;
				}
			}
		}

		squarez.pop();
		sq.unmark();
		return new ArrayList<Square>();

	}

	public static void main(String[] args){
		Boggle boggle = new Boggle( args[0] );
		BoggleFrame bFrame = new BoggleFrame( boggle );
		bFrame.pack();
		bFrame.setLocationRelativeTo(null);
		bFrame.setVisible(true);
//		System.out.println(foundWords);
//		System.out.println();
	}

}
