package com.dexterslab.tictactoe

import com.dexterslab.tictactoe.DataTypes._

object Api {

  def move(board: PlayableBoard, position: Position, player: Player): MoveResult = {
    board match {
      case EmptyBoard => SuccessfulMove(InPlayBoard(List(OccupiedCell(position, player))))
      case InPlayBoard(cells) => {
        if (cells.exists(cell => isOccupiedAtPosition(cell, position))) {
          InvalidMove
        } else {
          val newCells = OccupiedCell(position, player) :: cells
          if (newCells.count(isOccupied) == 9) {
            SuccessfulMove(FinishedBoard(newCells.map { case cell: OccupiedCell => cell }))
          } else {
            SuccessfulMove(InPlayBoard(newCells))
          }
        }
      }
    }
  }

  def checkWinningPosition(positions: List[Position]): Boolean = {
    def straightWinningPositions(rowsOrColumns: List[Int]) = {
      val ps = List(0,1,2)
      ps.map(p => rowsOrColumns.count(p == _)).count(_ == 3) == 1
    }

    val diagonalWinningPositions = positions.map(p => p.x - p.y).count(_ == 0) == 3 || positions.map(p => p.x + p.y).count(_ == 2) == 3

    straightWinningPositions(positions.map(_.x)) || straightWinningPositions(positions.map(_.y)) || diagonalWinningPositions
  }

  def whoWon(board: FinishedBoard): GameResult = {
    val positionsByPlayerO = board.cells.map(o => if(o.player == PlayerO) o.position else Position)
    val positionsByPlayerX = board.cells.map(o => if(o.player == PlayerX) o.position else Position)

    if(checkWinningPosition(positionsByPlayerO)) Winner(PlayerO)
    else if (checkWinningPosition(positionsByPlayerX)) Winner(PlayerX)
    else Draw
  }

  def playerAt(board: HasBeenPlayed, position: Position): Option[Player] = ???

  def takeBack(board: HasBeenPlayed): PlayableBoard = ???

  def isDraw(board: FinishedBoard): Boolean = ???

  private def isOccupied(cell: Cell): Boolean = cell match {
    case EmptyCell(_) => false
    case OccupiedCell(_, _) => true
  }

  private def isOccupiedAtPosition(cell: Cell, position: Position) = cell match {
    case OccupiedCell(p, _) if p == position  => true
    case _                                    => false
  }

}
