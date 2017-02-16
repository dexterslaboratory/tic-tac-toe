package com.dexterslab.tictactoe

import com.dexterslab.tictactoe.DataTypes._

object Api {

  private def hasSomeoneWon(occupiedCells: List[OccupiedCell]): Option[Player] = {
    def checkWinningPosition(positions: List[Position]): Boolean = {
      def horizontalOrVerticalWinningPositions(rowsOrColumns: List[Int]) = {
        val ps = List(0, 1, 2)
        ps.map(p => rowsOrColumns.count(p == _)).count(_ == 3) == 1
      }

      val horizontalWinningPositions = horizontalOrVerticalWinningPositions(positions.map(_.x))
      val verticalWinningPositions = horizontalOrVerticalWinningPositions(positions.map(_.y))
      val mainDiagonalWinningPositions = positions.map(p => p.x - p.y).count(_ == 0) == 3
      val crossDiagonalWinningPositions = positions.map(p => p.x + p.y).count(_ == 2) == 3
      horizontalWinningPositions ||
        verticalWinningPositions ||
        mainDiagonalWinningPositions ||
        crossDiagonalWinningPositions
    }

    val playersToOccupiedCells = occupiedCells.groupBy(_.player)
    val winningPlayer = for {
      playerToOccupiedCells <- playersToOccupiedCells if checkWinningPosition(playerToOccupiedCells._2.map(_.position))
    } yield playerToOccupiedCells._1
    winningPlayer.headOption
  }

  def move(board: PlayableBoard, position: Position, player: Player): MoveResult = {
    board match {
      case EmptyBoard => SuccessfulMove(InPlayBoard(List(OccupiedCell(position, player))))
      case InPlayBoard(cells) => {
        if (cells.exists(_.isOccupiedAtPosition(position))) {
          InvalidMove
        } else {
          val newCells = OccupiedCell(position, player) :: cells
          if (hasSomeoneWon(newCells.flatMap(_.getOccupiedCell)).nonEmpty) {
            SuccessfulMove(WinningBoard(newCells))
          } else if (newCells.count(_.isOccupied) == 9) {
            SuccessfulMove(FinishedBoard(newCells.map { case cell: OccupiedCell => cell }))
          } else {
            SuccessfulMove(InPlayBoard(newCells))
          }
        }
      }
    }
  }

  def whoWon(board: HasFinished): GameResult = {
    val maybePlayer = board match {
      case FinishedBoard(occupiedCells) => hasSomeoneWon(occupiedCells)

      case WinningBoard(cells) => {
        val occupiedCells = cells flatMap {
          case _: EmptyCell => None
          case occ: OccupiedCell => Some(occ)
        }
        hasSomeoneWon(occupiedCells)
      }
    }
    maybePlayer.map(Winner).getOrElse(Draw)
  }

  def playerAt(board: HasBeenPlayed, position: Position): Option[Player] = {
    val cell = board.cells.find(_.isOccupiedAtPosition(position))
    cell.flatMap(_.playerAt)
  }

  def takeBack(board: HasBeenPlayed): PlayableBoard = ???

  def isDraw(board: FinishedBoard): Boolean =
    whoWon(board) match {
      case Draw => true
      case _    => false
    }

}
