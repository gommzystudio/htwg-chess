package test.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import model.Position

class PositionSpec extends AnyWordSpec with Matchers {
  "A Position" when {
    "getCharX is called" should {
      "return the correct character for the x coordinate" in {
        Position(1, 1).getCharX() should be('a')
        Position(2, 1).getCharX() should be('b')
        Position(26, 1).getCharX() should be('z')
      }
    }
    "flipPosition is called" should {
      "return the flipped position" in {
        Position(1, 1).flipPosition() should be(Position(9, 9))
        Position(2, 1).flipPosition() should be(Position(8, 9))
        Position(26, 1).flipPosition() should be(Position(1, 9))
      }
    }
  }
  "The Position object" when {
    "fromChar is called" should {
      "return the correct Position for the given character and y coordinate" in {
        Position.fromChar('a', 1) should be(Position(1, 1))
        Position.fromChar('b', 1) should be(Position(2, 1))
        Position.fromChar('z', 1) should be(Position(26, 1))
      }
    }
  }
}
