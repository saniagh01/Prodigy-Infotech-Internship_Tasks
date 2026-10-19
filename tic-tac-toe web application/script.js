
/* =========================================
   TIC TAC TOE
   ========================================= */


// -----------------------------------------
// GAME VARIABLES
// -----------------------------------------

let board = [
    "", "", "",
    "", "", "",
    "", "", ""
];

let currentPlayer = "X";

let gameActive = true;

let gameMode = "ai";

let difficulty = "medium";


// -----------------------------------------
// SCORE
// -----------------------------------------

let scores = {
    player: 0,
    ai: 0,
    draws: 0
};


// -----------------------------------------
// WINNING COMBINATIONS
// -----------------------------------------

const winningCombinations = [

    [0, 1, 2],

    [3, 4, 5],

    [6, 7, 8],

    [0, 3, 6],

    [1, 4, 7],

    [2, 5, 8],

    [0, 4, 8],

    [2, 4, 6]

];


// -----------------------------------------
// DOM ELEMENTS
// -----------------------------------------

const cells =
    document.querySelectorAll(".cell");

const statusText =
    document.getElementById("statusText");

const statusIndicator =
    document.getElementById("statusIndicator");

const playerScore =
    document.getElementById("playerScore");

const aiScore =
    document.getElementById("aiScore");

const drawScore =
    document.getElementById("drawScore");

const newGameBtn =
    document.getElementById("newGameBtn");

const resetScoreBtn =
    document.getElementById("resetScoreBtn");

const themeBtn =
    document.getElementById("themeBtn");

const resultMessage =
    document.getElementById("resultMessage");

const resultIcon =
    document.getElementById("resultIcon");

const resultTitle =
    document.getElementById("resultTitle");

const resultDescription =
    document.getElementById("resultDescription");

const difficultySection =
    document.getElementById("difficultySection");

const opponentName =
    document.getElementById("opponentName");


// -----------------------------------------
// LOAD SCORE
// -----------------------------------------

function loadScores() {

    const savedScores =
        localStorage.getItem("ticTacToeScores");

    if (savedScores) {

        scores =
            JSON.parse(savedScores);

    }

    updateScoreboard();
}


// -----------------------------------------
// SAVE SCORE
// -----------------------------------------

function saveScores() {

    localStorage.setItem(
        "ticTacToeScores",
        JSON.stringify(scores)
    );
}


// -----------------------------------------
// UPDATE SCOREBOARD
// -----------------------------------------

function updateScoreboard() {

    playerScore.textContent =
        scores.player;

    aiScore.textContent =
        scores.ai;

    drawScore.textContent =
        scores.draws;
}


// -----------------------------------------
// CELL CLICK
// -----------------------------------------

cells.forEach(cell => {

    cell.addEventListener(
        "click",
        handleCellClick
    );

});


function handleCellClick(event) {

    const index =
        Number(event.currentTarget.dataset.index);


    // Ignore invalid moves

    if (!gameActive) {
        return;
    }


    if (board[index] !== "") {
        return;
    }


    // In AI mode only player can click

    if (
        gameMode === "ai" &&
        currentPlayer !== "X"
    ) {
        return;
    }


    makeMove(index, currentPlayer);


    const result =
        checkGameResult();


    if (result) {

        finishGame(result);

        return;
    }


    switchPlayer();


    // AI turn

    if (
        gameMode === "ai" &&
        currentPlayer === "O"
    ) {

        updateStatus("AI is thinking...");

        setTimeout(
            makeAIMove,
            500
        );
    }

}


// -----------------------------------------
// MAKE MOVE
// -----------------------------------------

function makeMove(index, player) {

    board[index] = player;

    const cell = cells[index];

    cell.textContent = player;

    cell.classList.add(
        "occupied",
        player.toLowerCase()
    );

    cell.disabled = true;
}


// -----------------------------------------
// SWITCH PLAYER
// -----------------------------------------

function switchPlayer() {

    currentPlayer =
        currentPlayer === "X"
            ? "O"
            : "X";


    if (gameMode === "pvp") {

        updateStatus(
            `${currentPlayer}'s turn`
        );

    }
    else {

        if (currentPlayer === "X") {

            updateStatus(
                "Your turn — make a move!"
            );

        }
    }
}


// -----------------------------------------
// CHECK RESULT
// -----------------------------------------

function checkGameResult() {

    for (
        const combination
        of winningCombinations
    ) {

        const [a, b, c] =
            combination;


        if (
            board[a] !== "" &&
            board[a] === board[b] &&
            board[a] === board[c]
        ) {

            return {
                winner: board[a],
                combination
            };

        }

    }


    // Draw

    if (!board.includes("")) {

        return {
            winner: "draw",
            combination: []
        };

    }


    return null;
}


// -----------------------------------------
// FINISH GAME
// -----------------------------------------

function finishGame(result) {

    gameActive = false;


    // Highlight winning cells

    result.combination.forEach(index => {

        cells[index].classList.add("win");

    });


    // DRAW

    if (result.winner === "draw") {

        scores.draws++;

        resultIcon.textContent = "🤝";

        resultTitle.textContent =
            "It's a Draw!";

        resultDescription.textContent =
            "Perfectly matched. Try again!";

        updateStatus("Game ended in a draw.");

    }


    // PLAYER WIN

    else if (
        result.winner === "X"
    ) {

        scores.player++;

        resultIcon.textContent = "🏆";

        resultTitle.textContent =
            "You Win!";

        resultDescription.textContent =
            "Excellent move. You outplayed the opponent!";

        updateStatus("Victory! 🎉");

    }


    // AI WIN

    else {

        scores.ai++;

        resultIcon.textContent = "🤖";

        resultTitle.textContent =
            "AI Wins!";

        resultDescription.textContent =
            "The AI got you this time. Try again!";

        updateStatus("AI wins!");

    }


    updateScoreboard();

    saveScores();

    resultMessage.classList.remove(
        "hidden"
    );
}


// -----------------------------------------
// UPDATE STATUS
// -----------------------------------------

function updateStatus(message) {

    statusText.textContent = message;

}


// -----------------------------------------
// NEW GAME
// -----------------------------------------

function newGame() {

    board = [
        "", "", "",
        "", "", "",
        "", "", ""
    ];

    currentPlayer = "X";

    gameActive = true;


    cells.forEach(cell => {

        cell.textContent = "";

        cell.disabled = false;

        cell.className = "cell";

    });


    resultMessage.classList.add(
        "hidden"
    );


    if (gameMode === "ai") {

        updateStatus(
            "Your turn — make a move!"
        );

    }
    else {

        updateStatus(
            "X's turn"
        );

    }

}


// -----------------------------------------
// RESET SCORE
// -----------------------------------------

function resetScores() {

    scores = {

        player: 0,

        ai: 0,

        draws: 0

    };


    saveScores();

    updateScoreboard();

    newGame();

}


// -----------------------------------------
// AI MOVE
// -----------------------------------------

function makeAIMove() {

    if (!gameActive) {
        return;
    }


    let move;


    if (difficulty === "easy") {

        move = randomMove();

    }

    else if (
        difficulty === "medium"
    ) {

        move =
            Math.random() < 0.65
                ? smartMove()
                : randomMove();

    }

    else {

        move = bestMove();

    }


    if (move !== undefined) {

        makeMove(move, "O");

    }


    const result =
        checkGameResult();


    if (result) {

        finishGame(result);

        return;
    }


    switchPlayer();

}


// -----------------------------------------
// RANDOM MOVE
// -----------------------------------------

function randomMove() {

    const available =
        getAvailableMoves();


    if (
        available.length === 0
    ) {

        return undefined;

    }


    const randomIndex =
        Math.floor(
            Math.random() *
            available.length
        );


    return available[randomIndex];
}


// -----------------------------------------
// GET AVAILABLE MOVES
// -----------------------------------------

function getAvailableMoves() {

    return board
        .map((value, index) =>
            value === ""
                ? index
                : null
        )
        .filter(index =>
            index !== null
        );

}


// -----------------------------------------
// SMART AI
// -----------------------------------------

function smartMove() {

    // 1. Try to win

    const winningMove =
        findWinningMove("O");

    if (winningMove !== null) {

        return winningMove;

    }


    // 2. Block player

    const blockingMove =
        findWinningMove("X");

    if (blockingMove !== null) {

        return blockingMove;

    }


    // 3. Take center

    if (board[4] === "") {

        return 4;

    }


    // 4. Take corner

    const corners =
        [0, 2, 6, 8]
            .filter(index =>
                board[index] === ""
            );


    if (corners.length > 0) {

        return corners[
            Math.floor(
                Math.random() *
                corners.length
            )
        ];

    }


    // 5. Random

    return randomMove();

}


// -----------------------------------------
// FIND WINNING MOVE
// -----------------------------------------

function findWinningMove(player) {

    const available =
        getAvailableMoves();


    for (const move of available) {

        board[move] = player;


        const result =
            checkGameResult();


        board[move] = "";


        if (
            result &&
            result.winner === player
        ) {

            return move;

        }

    }


    return null;
}


// -----------------------------------------
// UNBEATABLE AI
// MINIMAX
// -----------------------------------------

function bestMove() {

    let bestScore = -Infinity;

    let move;


    const available =
        getAvailableMoves();


    for (const index of available) {

        board[index] = "O";


        const score =
            minimax(
                board,
                0,
                false
            );


        board[index] = "";


        if (score > bestScore) {

            bestScore = score;

            move = index;

        }

    }


    return move;
}


// -----------------------------------------
// MINIMAX
// -----------------------------------------

function minimax(
    currentBoard,
    depth,
    isMaximizing
) {

    const result =
        evaluateBoard(
            currentBoard
        );


    if (result !== null) {

        return result;

    }


    if (isMaximizing) {

        let bestScore = -Infinity;


        for (
            const index
            of getEmptyIndexes(currentBoard)
        ) {

            currentBoard[index] = "O";


            const score =
                minimax(
                    currentBoard,
                    depth + 1,
                    false
                );


            currentBoard[index] = "";


            bestScore =
                Math.max(
                    bestScore,
                    score
                );

        }


        return bestScore;

    }


    else {

        let bestScore = Infinity;


        for (
            const index
            of getEmptyIndexes(currentBoard)
        ) {

            currentBoard[index] = "X";


            const score =
                minimax(
                    currentBoard,
                    depth + 1,
                    true
                );


            currentBoard[index] = "";


            bestScore =
                Math.min(
                    bestScore,
                    score
                );

        }


        return bestScore;

    }

}


// -----------------------------------------
// EVALUATE BOARD
// -----------------------------------------

function evaluateBoard(
    currentBoard
) {

    for (
        const combination
        of winningCombinations
    ) {

        const [a, b, c] =
            combination;


        if (
            currentBoard[a] !== "" &&
            currentBoard[a] ===
            currentBoard[b] &&
            currentBoard[a] ===
            currentBoard[c]
        ) {

            if (
                currentBoard[a] === "O"
            ) {

                return 10;

            }


            if (
                currentBoard[a] === "X"
            ) {

                return -10;

            }

        }

    }


    if (
        !currentBoard.includes("")
    ) {

        return 0;

    }


    return null;

}


// -----------------------------------------
// EMPTY INDEXES
// -----------------------------------------

function getEmptyIndexes(
    currentBoard
) {

    return currentBoard
        .map(
            (value, index) =>
                value === ""
                    ? index
                    : null
        )
        .filter(
            index =>
                index !== null
        );

}


// -----------------------------------------
// GAME MODE
// -----------------------------------------

document
    .querySelectorAll(".mode-btn")
    .forEach(button => {

        button.addEventListener(
            "click",
            () => {

                document
                    .querySelectorAll(
                        ".mode-btn"
                    )
                    .forEach(btn =>
                        btn.classList.remove(
                            "active"
                        )
                    );


                button.classList.add(
                    "active"
                );


                gameMode =
                    button.dataset.mode;


                if (
                    gameMode === "pvp"
                ) {

                    difficultySection.style.display =
                        "none";

                    opponentName.textContent =
                        "PLAYER 2";

                }

                else {

                    difficultySection.style.display =
                        "block";

                    opponentName.textContent =
                        "AI";

                }


                newGame();

            }
        );

    });


// -----------------------------------------
// DIFFICULTY
// -----------------------------------------

document
    .querySelectorAll(
        ".difficulty-btn"
    )
    .forEach(button => {

        button.addEventListener(
            "click",
            () => {

                document
                    .querySelectorAll(
                        ".difficulty-btn"
                    )
                    .forEach(btn =>
                        btn.classList.remove(
                            "active"
                        )
                    );


                button.classList.add(
                    "active"
                );


                difficulty =
                    button.dataset.difficulty;


                newGame();

            }
        );

    });


// -----------------------------------------
// NEW GAME BUTTON
// -----------------------------------------

newGameBtn.addEventListener(
    "click",
    newGame
);


// -----------------------------------------
// RESET SCORE BUTTON
// -----------------------------------------

resetScoreBtn.addEventListener(
    "click",
    resetScores
);


// -----------------------------------------
// THEME
// -----------------------------------------

themeBtn.addEventListener(
    "click",
    () => {

        document.body.classList.toggle(
            "light"
        );


        const isLight =
            document.body.classList.contains(
                "light"
            );


        themeBtn.textContent =
            isLight
                ? "☀️"
                : "🌙";


        localStorage.setItem(
            "ticTacToeTheme",
            isLight
                ? "light"
                : "dark"
        );

    }
);


// -----------------------------------------
// LOAD THEME
// -----------------------------------------

function loadTheme() {

    const theme =
        localStorage.getItem(
            "ticTacToeTheme"
        );


    if (theme === "light") {

        document.body.classList.add(
            "light"
        );

        themeBtn.textContent = "☀️";

    }

}


// -----------------------------------------
// START GAME
// -----------------------------------------

loadScores();

loadTheme();

newGame();