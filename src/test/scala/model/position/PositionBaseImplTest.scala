package model.position

import model.field.FieldInterface
import model.position.{PositionBaseImpl, PositionInterface}
import org.mockito.Mockito._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import util.color.Color
import org.mockito.ArgumentMatchers.any

class PositionBaseImplTest extends AnyWordSpec with Matchers with MockitoSugar {
  "PositionBaseImpl" should {
    "getCharX" should {
      "return the correct character for the x coordinate" in {
        val position = PositionBaseImpl(1, 1)
        position.getCharX() should be('a')
      }
    }

    "flipPosition" should {
      "return a new position with flipped x and y coordinates" in {
        val position = PositionBaseImpl(1, 1)
        val flippedPosition = position.flipPosition()
        flippedPosition.getX() should be(8)
        flippedPosition.getY() should be(8)
      }
    }

    "getX" should {
      "return the x coordinate" in {
        val position = PositionBaseImpl(1, 1)
        position.getX() should be(1)
      }
    }

    "getY" should {
      "return the y coordinate" in {
        val position = PositionBaseImpl(1, 1)
        position.getY() should be(1)
      }
    }

    "fromChar" should {
      "return a new PositionBaseImpl with the correct x and y coordinates" in {
        val position = PositionBaseImpl.fromChar('a', 1)
        position.getX() should be(1)
        position.getY() should be(1)
      }
    }
  }
}
