package controller

import model.gamestate.GameStateInterface
import model.pieces.Piece
import model.position.PositionInterface
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.mockito.ArgumentMatchers._
import scala.util.{Success, Failure}
import util.fileio.FileIO
import model.field.FieldInterface
import model.commands.MoveCommand
import util.color.Color
import util.view.ViewInterface

class ControllerBaseImplTest
    extends AnyWordSpec
    with Matchers
    with MockitoSugar {

  "ControllerBaseImpl" should {

    "load game state from a file" in {
      val mockGameState = mock[GameStateInterface]
      val mockFileIO = mock[FileIO]
      val controller = new ControllerBaseImpl(mockGameState, mockFileIO)
      val path = "testPath"
      val newState = mock[GameStateInterface]

      when(mockFileIO.read(path)).thenReturn(newState)

      controller.load(path)

      verify(mockFileIO).read(path)
      controller.gameState shouldBe newState
    }

    "add a view and update it" in {
      val mockGameState = mock[GameStateInterface]
      val mockFileIO = mock[FileIO]
      val controller = new ControllerBaseImpl(mockGameState, mockFileIO)
      val mockView = mock[ViewInterface]

      controller.addViewAndUpdate(mockView)

      verify(mockView).update(mockGameState)
    }

    "save game state to a file" in {
      val mockGameState = mock[GameStateInterface]
      val mockFileIO = mock[FileIO]
      val controller = new ControllerBaseImpl(mockGameState, mockFileIO)
      val path = "testPath"

      controller.save(path)

      verify(mockFileIO).write(path, mockGameState)
    }

    "undo a command" in {
      val mockGameState = mock[GameStateInterface]
      val mockUndoGameState = mock[GameStateInterface]
      val controller = new ControllerBaseImpl(mockGameState, mock[FileIO])

      when(mockGameState.undoCommand()).thenReturn(mockUndoGameState)

      controller.undoCommand()

      controller.gameState shouldBe mockUndoGameState
    }

    "redo a command" in {
      val mockGameState = mock[GameStateInterface]
      val mockRedoGameState = mock[GameStateInterface]
      val controller = new ControllerBaseImpl(mockGameState, mock[FileIO])

      when(mockGameState.redoCommand()).thenReturn(mockRedoGameState)

      controller.redoCommand()

      controller.gameState shouldBe mockRedoGameState
    }

    "execute a valid move command" in {
      val from = mock[PositionInterface]
      val to = mock[PositionInterface]
      val piece = mock[Piece]
      val mockGameState = mock[GameStateInterface]
      val mockField = mock[FieldInterface]
      val controller = new ControllerBaseImpl(mockGameState, mock[FileIO])

      when(mockGameState.getField()).thenReturn(mockField)
      when(mockGameState.executeCommand(any[MoveCommand]))
        .thenReturn(mockGameState)
      when(mockField.getPiece(from)).thenReturn(Some(piece))
      when(piece.availableMoves(from, mockField)).thenReturn(List(to))
      when(mockField.getCurrentPlayer()).thenReturn(Color.White)
      when(piece.color).thenReturn(Color.White)

      controller.runMoveCommand(from, to)

      verify(mockGameState).executeCommand(
        any[MoveCommand]
      )
    }

    "execute a move command but not update the game state if it would result in a check" in {
      val from = mock[PositionInterface]
      val to = mock[PositionInterface]
      val piece = mock[Piece]
      val mockGameState = mock[GameStateInterface]
      val mockField = mock[FieldInterface]
      val controller = new ControllerBaseImpl(mockGameState, mock[FileIO])

      when(mockGameState.getField()).thenReturn(mockField)
      when(mockGameState.executeCommand(any[MoveCommand]))
        .thenReturn(mockGameState)
      when(mockField.getPiece(from)).thenReturn(Some(piece))
      when(piece.availableMoves(from, mockField)).thenReturn(List(to))
      when(mockField.getCurrentPlayer()).thenReturn(Color.White)
      when(piece.color).thenReturn(Color.White)
      when(mockField.isCheck(Color.White)).thenReturn(true)

      controller.runMoveCommand(from, to)

      controller.gameState shouldBe mockGameState
    }

    "not execute an invalid move command" in {
      val from = mock[PositionInterface]
      val to = mock[PositionInterface]
      val piece = mock[Piece]
      val mockGameState = mock[GameStateInterface]
      val mockField = mock[FieldInterface]
      val controller = new ControllerBaseImpl(mockGameState, mock[FileIO])

      when(mockGameState.getField()).thenReturn(mockField)
      when(mockField.getPiece(from)).thenReturn(Some(piece))
      when(piece.availableMoves(from, mockField))
        .thenReturn(List.empty[PositionInterface])
      when(piece.color).thenReturn(Color.White)

      controller.runMoveCommand(from, to)

      verify(mockGameState, never()).executeCommand(any[MoveCommand])
    }

    "handle a failure in fetching a piece for a move command" in {
      val from = mock[PositionInterface]
      val to = mock[PositionInterface]
      val mockGameState = mock[GameStateInterface]
      val mockField = mock[FieldInterface]
      val controller = new ControllerBaseImpl(mockGameState, mock[FileIO])

      when(mockGameState.getField()).thenReturn(mockField)
      when(mockField.getPiece(from)).thenReturn(None)

      controller.runMoveCommand(from, to)

      verify(mockGameState, never()).executeCommand(any[MoveCommand])
    }

    "return the game state" in {
      val mockGameState = mock[GameStateInterface]
      val controller = new ControllerBaseImpl(mockGameState, mock[FileIO])

      controller.getGameState() shouldBe mockGameState
    }
  }
}
