import model.pieces.{Piece, Rook}
import org.scalatest.wordspec.AnyWordSpec
import model.*
import model.pieces.*
import controller.Controller
import view.TUI
import scala.io.StdIn
import util.View

class PieceSpec extends AnyWordSpec {
  "A Piece" when {
    "created" should {
      "throw NotImplementedError when getSymbol is called" in {
        assertThrows[NotImplementedError] {
          val piece = new Piece(Color.White)
          piece.getSymbol()
        }
      }
      "throw NotImplementedError when availableMoves is called" in {
        assertThrows[NotImplementedError] {
          val piece = new Piece(Color.White)
          piece.availableMoves(null, null)
        }
      }
    }
  }

  "A Field" when {
    "created" should {
      "return an map with 32 pieces when pieces is called" in {
        val field = new Field(null)
        assert(field.pieces.size === 32)
      }
    }
    "gives a piece" should {
      "return the piece" in {
        val field = new Field(null)
        val piece = field.getPiece(1, 1)
        assert(piece.get === field.pieces.get(new Position(1, 1)).get)
      }
    }
    "can check move leglity" should {
      "return true when the move is legal" in {
        val field = new Field(null)
        val piece = field.getPiece(1, 2).get
        val from = new Position(1, 2)
        val to = new Position(1, 3)
        assert(field.checkLegality(piece, from, to) === true)
      }
      "return false when the move is illegal" in {
        val field = new Field(null)
        val piece = field.getPiece(1, 2).get
        val from = new Position(1, 2)
        val to = new Position(1, 1)
        assert(field.checkLegality(piece, from, to) === false)
      }
    }
    "when movePiece is called" should {
      "return a new Field with the piece moved" in {
        val field = new Field(null)
        val from = new Position(1, 2)
        val to = new Position(1, 3)
        val newField = field.movePiece(from, to)
        assert(newField.pieces.get(to).get === field.pieces.get(from).get)
      }
      "do nothing when there is no piece at the from position" in {
        val field = new Field(null)
        val from = new Position(1, 3)
        val to = new Position(1, 4)
        val newField = field.movePiece(from, to)
        assert(newField.pieces.get(to) === None)
      }
    }
    "a field can be created with a custom map" should {
      "return a new Field with the custom map" in {
        val map = Map(new Position(1, 1) -> new Rook(Color.White))
        val field = new Field(map)
        assert(
          field.pieces.get(new Position(1, 1)).get === map
            .get(new Position(1, 1))
            .get
        )
      }
    }
  }

  "A position" when {
    "created" should {
      "return a string representation of the position" in {
        val position = Position(1, 1)
        assert(position.getCharX() === 'a')
        assert(position.y === 1)
      }
    }
    "can be created with a char and an int" should {
      "return a new Position with the char and int" in {
        val position = Position.fromChar('a', 1)
        assert(position.x === 1)
        assert(position.x === 1)
      }
    }
  }

  "A Controller" when {
    "created" should {
      "be able to start a game" in {
        val controller = new Controller()
        controller.startGame()
        assert(controller.currentField != null)
      }
    }
    "a game is started" should {
      "be able to move a piece" in {
        val controller = new Controller()
        controller.startGame()
        val from = new Position(1, 2)
        val to = new Position(1, 3)
        val lastField = controller.currentField;
        controller.movePiece(from, to)
        assert(controller.currentField != lastField)
      }
    }
  }

  "A generic view" when {
    "created" should {
      "be able to subscribe to a controller" in {
        val controller = new Controller()
        val view = new View(controller)
        assert(controller.views.contains(view))
      }
      "can wait for input and exit" in {
        val controller = new Controller()
        val view = new View(controller)
        view.waitForInput()
      }
      "can update" in {
        val controller = new Controller()
        val view = new View(controller)
        view.update(controller.currentField)
      }
    }
  }

  "A TUI" when {
    "created" should {
      "be able to print a field" in {
        val controller = new Controller()
        controller.startGame()
        val view = new TUI(controller)
        view.printField(controller.currentField)
      }
      "be able to wait for input and move a piece" in {
        val controller = new Controller()
        controller.startGame()
        val view = new TUI(controller)
        view.waitForInput(List("a2a3", "exit"))
        assert(
          controller.currentField.pieces.get(new Position(1, 3)).get != null
        )
      }
    }
  }
}
