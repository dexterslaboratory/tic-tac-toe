package com.dexterslab.tictactoe

import com.dexterslab.tictactoe.DataTypes._

object Api {

  def move(board: PlayableBoard, position: Position, player: Player): MoveResult = {
    board match {
      case EmptyBoard => SuccessfulMove(InPlayBoard(List(OccupiedCell(position, player))))
      case InPlayBoard(cells) => {
        val newCells = OccupiedCell(position, player) :: cells
        if (newCells.count(isOccupied) == 9) {
          SuccessfulMove(FinishedBoard(newCells.map { case cell: OccupiedCell => cell }))
        } else {
          SuccessfulMove(InPlayBoard(newCells))
        }
      }
    }
  }

  def whoWon(board: FinishedBoard): GameResult = ???

  def playerAt(board: HasBeenPlayed, position: Position): Option[Player] = ???

  def takeBack(board: HasBeenPlayed): PlayableBoard = ???

  def isDraw(board: FinishedBoard): Boolean = ???

  private def isOccupied(cell: Cell): Boolean = cell match {
    case EmptyCell(_) => false
    case OccupiedCell(_, _) => true
  }

}
