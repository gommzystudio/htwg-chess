package test.model

import org.scalatest.wordspec.AnyWordSpec
import model.FieldFactory

class FieldFactorySpec extends AnyWordSpec {
  "A FieldFactorySpec" when {
    "createInitialField called" should {
      "return an field with 32 pieces" in {
        val field = FieldFactory.createInitialField();
        assert(field.pieces.size === 32)
      }
    }
  }
}
