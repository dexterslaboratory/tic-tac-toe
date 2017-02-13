package com.dexterslab.tictactoe

import com.dexterslab.tictactoe.DataTypes._

object Api {

  def move(board: PlayableBoard, position: Position): Board = ???

  def whoWon(board: FinishedBoard): GameResult = ???

  def playerAt(board: HasBeenPlayed, position: Position): Option[Player] = ???

  def takeBack(board: HasBeenPlayed): PlayableBoard = ???

  def isDraw(board: FinishedBoard): Boolean = ???

}
