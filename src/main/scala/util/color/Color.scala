package util.color

enum Color:
  case Black, White;

def flipColor(color: Color): Color = {
  if (color == Color.White) {
    return Color.Black
  } else {
    return Color.White
  }
}
