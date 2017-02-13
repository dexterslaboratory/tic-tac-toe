package com.dexterslab.tictactoe

object DataTypes {

  sealed trait Player
  case object PlayerX extends Player
  case object PlayerO extends Player

  case class Position(x: Int, y: Int)

  sealed trait Cell
  case class EmptyCell(position: Position) extends Cell
  case class OccupiedCell(position: Position, player: Player) extends Cell

  sealed trait Board
  sealed trait PlayableBoard extends Board
  sealed trait HasBeenPlayed extends Board

  case object EmptyBoard extends PlayableBoard
  case class InPlayBoard(cells: List[Cell]) extends PlayableBoard with HasBeenPlayed
  case class FinishedBoard(cells: List[OccupiedCell]) extends HasBeenPlayed

  sealed trait GameResult
  case class Winner(player: Player) extends GameResult
  case object Draw extends GameResult

}
