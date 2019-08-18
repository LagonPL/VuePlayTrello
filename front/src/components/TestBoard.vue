<template>
	<div id="board-component">
		<div id="board">
			<div id="board-buttons">
			<button id="boardTitle" @click="renameBoard()">{{  this.boards.title  }}</button><button id="fav-btn">&#9959</button>
			</div>
			<div id="list" v-for="(list, listIndex) in boards.lists">
				<p class="listTitle">
					{{list.title}}
					<button id="renameListButton" class="listButton" @click="renameList(listIndex)">...</button>
					<button id="deleteListButton" class="listButton" @click="deleteList(listIndex)">X</button>
				</p>
				<div id="card" v-for="(card, cardIndex) in list.cards">
					<button class="cardButton" @click="openCard(listIndex, cardIndex)">{{card.title}}</button>
					<button id="editCardButton" @click="editCard(listIndex, cardIndex)">...</button>
				</div>
				<button id="addCardButton" @click="addCard(listIndex)">Dodaj kartę...</button>
			</div>
		<button id="addListButton" @click="addList">Dodaj listę...</button>
		</div>
	</div>
</template>

<script>
export default {
	data: function () {
		return {
			boards: 
				{title: 'Tab1',
				 lists: [
					{title: 'list1.1',
					 cards: [
						{title: 'card1.1.1'},
						{title: 'card1.1.2'},
						{title: 'card1.1.3'}
					 ]},
					{title: 'list1.2',					 
					 cards: [
						{title: 'card1.2.1'},
						{title: 'card1.2.2'},
						{title: 'card1.2.3'}
					 ]},
					{title: 'list1.3',				
					 cards: [
						{title: 'card1.3.1'},
						{title: 'card1.3.2'},
						{title: 'card1.3.3'}
					 ]}
				 ]}
		}
	},
	methods: {
		renameBoard: function() {
			var newTitle = prompt("Podaj nową nazwę", this.boards.title);
			this.boards.title = newTitle;
		},
		addList: function() {
			var newTitle = prompt("Podaj nazwę nowej listy");
			this.boards.lists.push({title: newTitle, cards: []});
		},
		addCard: function(listIndex) {
			var newTitle = prompt("Podaj nazwę nowej karty");
			console.log(listIndex);
			this.boards.lists[listIndex].cards.push({title: newTitle});
		},
		deleteList: function(listIndex) {
			this.boards.lists.splice(listIndex, 1);
		},
		renameList: function(listIndex) {
			var newTitle = prompt("Podaj nową nazwę", this.boards.lists[listIndex].title);
			this.boards.lists[listIndex].title = newTitle;
		},
		openCard: function(listIndex, cardIndex) {
			alert(this.boards.lists[listIndex].cards[cardIndex].title);
		},
		editCard: function(listIndex, cardIndex) {
			var newTitle = prompt("Podaj nową nazwę", this.boards.lists[listIndex].cards[cardIndex].title);
			this.boards.lists[listIndex].cards[cardIndex].title = newTitle;
		}
	}
}
</script>

<style scoped lang="scss">	
	#fav-btn {
		background-color: transparent;
		font-size: 22px;
		padding: 0 10px;
		margin: 0;
		color: #f6f6f6;
		border-radius: 3px;
	}
	
	#fav-btn:hover {
		background-color: #026AA7;
	}

	p {
		margin: 0;
		padding: 3px 5px;
	}
	
	#board {
		padding: 5px;
		background-color: #0079BF;
	}
	
	#boardTitle {
	    font-size: 18px;
		font-weight: 700;
		line-height: 30px;
		color: #f6f6f6;
		padding: 0 10px;
		background-color: transparent;
		border-radius: 3px;
		margin-right: 10px;
	}
	
	#boardTitle:hover {
		background-color: #026AA7;
	}
	
	#list {
		display: inline-block;
		background-color: #E2E4E6;
		border-style: none;
		border-radius: 3px;
		padding: 5px;
		margin: 5px;
		vertical-align: top;
		height: 100%;
		width: 270px;
		white-space: nowrap;
		box-sizing: border-box;
		color: #4d4d4d;
	}
	
	.listTitle {
		margin-left: 0px;
		font-weight: bold;
	}
	
	#card {
		font-weight: normal;
		font-style: italic;
		background-color: white;
		border-style: none;
		border-radius: 2px;
		padding: 6px 6px 2px 8px;
		margin: 4px 2px 6px 4px;
		max-width: 300px;
		min-height: 20px;
		position: relative;
		color: #4d4d4d;
	}
	
	#card:hover {
		background-color: #EDEFF0;
	}
	
	#addCardButton {
		background-color: transparent;
		color: #838c91;
		width: 100%;
		text-align: left;
	}
	
	#addCardButton:hover {
		background-color: #CDD2D4;
		color: #818384;
		text-decoration: underline;
	}
	
	#addListButton {
		display: inline-block;
		background-color: rgba(0,0,0,.1);
		border-radius: 2px;
		padding: 5px;
		margin: 5px;
		vertical-align: top;
		height: 100%;
		width: 270px;
		white-space: nowrap;
		box-sizing: border-box;
		color: rgba(255,255,255,.7);
		text-align: left;
	}
	
	#addListButton:hover {
		background-color: rgba(0,0,0,.3);
	}

	.listButton {
		background-color: transparent;
		float: right;
	}
	
	.listButton:hover {
		background-color: #CDD2D4;
		text-decoration: underline;
	}
	
	#deleteListButton {
		color: maroon;	
	}
	
	#deleteListButton:hover {
		color: red;
	}
	
	#renameListButton {
		color: #838c91;	
	}
	
	#renameListButton:hover {
		color: #818384;
	}
	
	.cardButton {
		background-color: transparent;
		width: 100%;
		text-align: left;
		padding: 0;
		color: #4d4d4d;
	}
	
	#editCardButton {
		background-color: transparent;
		position: absolute;
		top: 0;
		right: 0;
		margin: 0;
		display: none;
		color: #4d4d4d;
	}
	
	#card:hover > #editCardButton {
		display: inline;
	}
	
</style>