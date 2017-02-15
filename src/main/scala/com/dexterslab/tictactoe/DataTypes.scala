package com.dexterslab.tictactoe

object DataTypes {

  sealed trait Player
  case object PlayerX extends Player
  case object PlayerO extends Player

  sealed trait MoveResult
  case object InvalidMove extends MoveResult
  case class SuccessfulMove(board: HasBeenPlayed) extends MoveResult

  case class Position(x: Int, y: Int)

  sealed trait Cell {
    def position: Position

    def playerAt: Option[Player] =  this match {
      case EmptyCell(_) => None
      case OccupiedCell(_, player) => Some(player)
    }
  }
  case class EmptyCell(override val position: Position) extends Cell
  case class OccupiedCell(override val position: Position, player: Player) extends Cell

  sealed trait Board {
    def cells: List[Cell]
  }
  sealed trait PlayableBoard extends Board
  sealed trait HasBeenPlayed extends Board
  sealed trait HasFinished extends Board

  case object EmptyBoard extends PlayableBoard {
    override val cells: List[EmptyCell] = {
      val startingPositions =
        (for {
          r <- 0 to 2
          c <- 0 to 2
        } yield Position(r, c)).toList
      startingPositions.map(EmptyCell)
    }
  }
  case class InPlayBoard(override val cells: List[Cell]) extends PlayableBoard with HasBeenPlayed
  case class WinningBoard(override val cells: List[Cell]) extends HasBeenPlayed with HasFinished
  case class FinishedBoard(override val cells: List[OccupiedCell]) extends HasBeenPlayed with HasFinished

  sealed trait GameResult
  case class Winner(player: Player) extends GameResult
  case object Draw extends GameResult

}
