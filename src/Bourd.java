public class Bourd {
    Pieces[][] grid;

    Queen whightQueen = new Queen("W");
    King whightKing = new King("W");
    Rook whightRook = new Rook("W");
    Knight whightKnight = new Knight("W");
    Bishop whightBishop = new Bishop("W");
    Pawn whightPawn = new Pawn("W");

    Queen blackQueen = new Queen("B");
    King blackKing = new King("B");
    Rook blackRook = new Rook("B");
    Knight blackKnight = new Knight("B");
    Bishop blackBishop = new Bishop("B");
    Pawn blackPawn = new Pawn("B");

    Empty empty = new Empty("E");
    public void setBourd(Pieces[][] grid){
        this.grid = grid;
    }
    public Pieces[][] getBourd(){
        return grid;
    }
    public void PrintBourd(){
        for (int i = 0; i < grid.length; i++) {
            System.out.print(8-i);
            for (int j = 0; j < grid.length; j++) {
                System.out.print(grid[i][j].image);
            }
            System.out.println("");
        }
        System.out.println("  a b c d e f g h");
    }
    public void RestBourd(){
        grid = new Pieces[][]{
                {whightRook, whightKnight, whightBishop, whightQueen, whightKing, whightBishop, whightKnight, whightRook},
                {whightPawn, whightPawn, whightPawn, whightPawn, whightPawn, whightPawn, whightPawn, whightPawn},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {empty, empty, empty, empty, empty, empty, empty, empty},
                {blackPawn, blackPawn, blackPawn, blackPawn, blackPawn, blackPawn, blackPawn, blackPawn},
                {blackRook, blackKnight, blackBishop, blackQueen, blackKing, blackBishop, blackKnight, blackRook},
        };
    }
    //public int[] getScore(){}
}
