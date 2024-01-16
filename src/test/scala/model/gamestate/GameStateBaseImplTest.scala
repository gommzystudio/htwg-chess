package model.gamestate

import model.commands.Command
import model.field.FieldInterface
import org.mockito.Mockito._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import scala.util.Success

class GameStateBaseImplTest
    extends AnyWordSpec
    with Matchers
    with MockitoSugar {
  "A GameStateBaseImpl" when {
    "executing a command" should {
      "update the state with the new field and modify the undo stack" in {
        val initialField = mock[FieldInterface]
        val newField = mock[FieldInterface]
        val command = mock[Command]
        when(command.execute()).thenReturn(Success(newField))

        val gameState = new GameStateBaseImpl(initialField)
        val updatedGameState = gameState.executeCommand(command)

        updatedGameState.getField() shouldBe newField
        updatedGameState.getUndoStack() should contain theSameElementsAs List(
          command
        )
        updatedGameState.getRedoStack() shouldBe empty
      }
    }

    "undoing a command" should {
      "revert to the previous state and update stacks" in {
        val initialField = mock[FieldInterface]
        val previousField = mock[FieldInterface]
        val command = mock[Command]
        when(command.undo()).thenReturn(Success(previousField))

        val gameState = new GameStateBaseImpl(initialField, List(command))
        val updatedGameState = gameState.undoCommand()

        updatedGameState.getField() shouldBe previousField
        updatedGameState.getUndoStack() shouldBe empty
        updatedGameState.getRedoStack() should contain theSameElementsAs List(
          command
        )
      }

      "remain unchanged when there is no command to undo" in {
        val initialField = mock[FieldInterface]
        val gameState = new GameStateBaseImpl(initialField)
        val updatedGameState = gameState.undoCommand()

        updatedGameState shouldBe gameState
      }
    }

    "redoing a command" should {
      "re-execute the last undone command and update stacks" in {
        val initialField = mock[FieldInterface]
        val redoneField = mock[FieldInterface]
        val command = mock[Command]
        when(command.execute()).thenReturn(Success(redoneField))

        val gameState =
          new GameStateBaseImpl(initialField, List(), List(command))
        val updatedGameState = gameState.redoCommand()

        updatedGameState.getField() shouldBe redoneField
        updatedGameState.getUndoStack() should contain theSameElementsAs List(
          command
        )
        updatedGameState.getRedoStack() shouldBe empty
      }

      "remain unchanged when there is no command to redo" in {
        val initialField = mock[FieldInterface]
        val gameState = new GameStateBaseImpl(initialField)
        val updatedGameState = gameState.redoCommand()
        updatedGameState shouldBe gameState
      }
    }

    "getting the current field" should {
      "return the current field state" in {
        val field = mock[FieldInterface]
        val gameState = new GameStateBaseImpl(field)

        gameState.getField() shouldBe field
      }
    }

    "getting the undo stack" should {
      "return the current undo stack" in {
        val command = mock[Command]
        val gameState =
          new GameStateBaseImpl(mock[FieldInterface], List(command))

        gameState.getUndoStack() should contain theSameElementsAs List(command)
      }
    }

    "getting the redo stack" should {
      "return the current redo stack" in {
        val command = mock[Command]
        val gameState =
          new GameStateBaseImpl(mock[FieldInterface], List(), List(command))

        gameState.getRedoStack() should contain theSameElementsAs List(command)
      }
    }
  }
}
