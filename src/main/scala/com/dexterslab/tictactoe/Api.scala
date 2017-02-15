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

  def whoWon(board: HasFinished): GameResult = {

    def horizontalOrVerticalWinningPositions(rowsOrColumns: List[Int]) = {
      val ps = List(0, 1, 2)
      ps.map(p => rowsOrColumns.count(p == _)).count(_ == 3) == 1
    }

    def checkWinningPosition(positions: List[Position]): Boolean = {
      val horizontalWinningPositions = horizontalOrVerticalWinningPositions(positions.map(_.x))
      val verticalWinningPositions = horizontalOrVerticalWinningPositions(positions.map(_.y))
      val mainDiagonalWinningPositions = positions.map(p => p.x - p.y).count(_ == 0) == 3
      val crossDiagonalWinningPositions = positions.map(p => p.x + p.y).count(_ == 2) == 3
      horizontalWinningPositions ||
        verticalWinningPositions ||
          mainDiagonalWinningPositions ||
            crossDiagonalWinningPositions
    }

    def findGameResult(occupiedCells: List[OccupiedCell]): GameResult = {
      val playersToOccupiedCells = occupiedCells.groupBy(_.player)
      val winningPlayer = for {
        playerToOccupiedCells <- playersToOccupiedCells if checkWinningPosition(playerToOccupiedCells._2.map(_.position))
      } yield Winner(playerToOccupiedCells._1)
      winningPlayer.headOption.getOrElse(Draw)
    }

    board match {
      case FinishedBoard(occupiedCells) => findGameResult(occupiedCells)

      case WinningBoard(cells) => {
        val occupiedCells = cells flatMap {
          case _: EmptyCell => None
          case occ: OccupiedCell => Some(occ)
        }
        findGameResult(occupiedCells)
      }
    }
  }

  def playerAt(board: HasBeenPlayed, position: Position): Option[Player] = ???

  def takeBack(board: HasBeenPlayed): PlayableBoard = ???

  def isDraw(board: FinishedBoard): Boolean = ???

  private def isOccupied(cell: Cell): Boolean = cell match {
    case EmptyCell(_) => false
    case OccupiedCell(_, _) => true
  }

  private def isOccupiedAtPosition(cell: Cell, position: Position) = cell match {
    case OccupiedCell(p, _) if p == position => true
    case _ => false
  }

}
