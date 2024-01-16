package util.color

import model.field.FieldInterface
import model.position.{PositionBaseImpl, PositionInterface}
import org.mockito.Mockito._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import util.color.Color
import org.mockito.ArgumentMatchers.any

class ColorTest extends AnyWordSpec with Matchers with MockitoSugar {
  "flipColor" should {
    "return Black when input is White" in {
      val color = Color.White
      val flippedColor = flipColor(color)
      flippedColor should be(Color.Black)
    }

    "return White when input is Black" in {
      val color = Color.Black
      val flippedColor = flipColor(color)
      flippedColor should be(Color.White)
    }
  }
}
