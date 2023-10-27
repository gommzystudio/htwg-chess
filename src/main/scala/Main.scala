@main
def start = {
  var field = Field(null);

  while (true) {
    field.printField();
    println("Enter move (e.g. a2a3): ");
    println("Conflict 2");
    val input = scala.io.StdIn.readLine();
    val from = Position(input.charAt(0), input.charAt(1).asDigit);
    val to = Position(input.charAt(2), input.charAt(3).asDigit);

    field = field.movePiece(from, to);
  }
}
