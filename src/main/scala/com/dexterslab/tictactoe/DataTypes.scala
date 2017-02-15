package com.dexterslab.tictactoe

object DataTypes {

  sealed trait Player
  case object PlayerX extends Player
  case object PlayerO extends Player

  sealed trait MoveResult
  case object InvalidMove extends MoveResult
  case class SuccessfulMove(board: HasBeenPlayed) extends MoveResult

  case class Position(x: Int, y: Int)

  sealed trait Cell
  case class EmptyCell(position: Position) extends Cell
  case class OccupiedCell(position: Position, player: Player) extends Cell

  sealed trait Board
  sealed trait PlayableBoard extends Board
  sealed trait HasBeenPlayed extends Board
  sealed trait HasFinished extends Board

  case object EmptyBoard extends PlayableBoard
  case class InPlayBoard(cells: List[Cell]) extends PlayableBoard with HasBeenPlayed
  case class WinningBoard(cells: List[Cell]) extends HasBeenPlayed with HasFinished
  case class FinishedBoard(cells: List[OccupiedCell]) extends HasBeenPlayed with HasFinished

  sealed trait GameResult
  case class Winner(player: Player) extends GameResult
  case object Draw extends GameResult

}
