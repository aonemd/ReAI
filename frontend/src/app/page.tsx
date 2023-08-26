"use client";

import { useState, useEffect } from "react";

type Cell = {
  x: number;
  y: number;
};
const isSameCell = (a: Cell, b: Cell) => a.x === b.x && a.y === b.y;

type NodeState = {
  ironManPosition: Cell;
  thanosPosition: Cell;
  stonePositions: Cell[];
  warriorPositions: Cell[];
};
type Node = {
  state: NodeState;
  operator: {
    name: string;
  };
};
type SearchResult = {
  path: Node[];
  gridWidth: number;
  gridHeight: number;
  score: number;
  numOfNodes: number;
  algo: string;
};

type GridProps = {
  node: Node;
  w: number;
  h: number;
};
const Grid = ({ node, w, h }: GridProps) => {
  let operator = node.operator;

  return (
    <div className="">
      <div className="flex flex-row justify-center items-center my-2">
        <span className="font-semibold">{operator.name}</span>
      </div>

      {[...Array(w)].map((_, i) => {
        return (
          <div className="flex flex-row justify-center items-center" key={i}>
            {Array(h)
              .fill("-")
              .map((_, j) => {
                return (
                  <GridCell key={`${i}${j}`} i={i} j={j} state={node.state} />
                );
              })}
          </div>
        );
      })}
    </div>
  );
};

type CellProps = {
  state: NodeState;
  i: number;
  j: number;
};
const GridCell = ({ state, i, j }: CellProps) => {
  let ironMan = state.ironManPosition;
  let thanos = state.thanosPosition;
  let stonePositions = state.stonePositions;
  let warriorPositions = state.warriorPositions;

  let cellValue;
  let curCell: Cell = { x: i, y: j };

  if (stonePositions.some((s) => isSameCell(curCell, s))) cellValue = "💎";
  if (warriorPositions.some((w) => isSameCell(curCell, w))) cellValue = "👾";
  if (isSameCell(curCell, thanos)) cellValue = "🧌";
  if (isSameCell(curCell, ironMan)) cellValue = "🦹";

  return (
    <div className="flex items-center justify-center box-border h-32 w-32 border-4 text-6xl">
      {cellValue}
    </div>
  );
};

export default function Home() {
  const [strategies, setStrategies] = useState<Array<string>>([]);
  const [algo, setAlgo] = useState("bfs");
  const [searchResult, setSearchResult] = useState<SearchResult | null>(null);
  const [isLoading, setLoading] = useState(true);
  const [currentNodeIndex, setCurrentNodeIndex] = useState(0);
  const [paused, setPaused] = useState(true);

  async function getAlgos() {
    try {
      const res = await fetch(
        `http://localhost:8080/api/endgame/search/strategies`
      );
      const data = await res.json();

      setStrategies(data);
      setLoading(false);
    } catch (err) {
      console.log(err);
    }
  }

  async function getSearchResult() {
    try {
      const res = await fetch(
        `http://localhost:8080/api/endgame/search?grid=5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3&algo=${algo}`
      );
      const data = await res.json();
      console.log(data);
      setSearchResult(data);
      setCurrentNodeIndex(0);
      setLoading(false);
    } catch (err) {
      console.log(err);
    }
  }

  useEffect(() => {
    getAlgos();
    getSearchResult();
  }, []);

  useEffect(() => {
    var timeout: any;
    if (!paused) {
      timeout = setTimeout(function () {
        if (currentNodeIndex >= searchResult!.path.length - 1) {
          setPaused(true);
        } else {
          const next = currentNodeIndex + 1;
          setCurrentNodeIndex(next);
        }
      }, 300);
    }
    return function () {
      clearTimeout(timeout);
    };
  }, [paused, currentNodeIndex]);

  function searchAndPlay() {
    getSearchResult();
    play();
  }
  function play() {
    setPaused(!paused);
  }
  function next() {
    setCurrentNodeIndex(currentNodeIndex + 1);
  }
  function previous() {
    setCurrentNodeIndex(currentNodeIndex - 1);
  }

  if (isLoading) return <p>Loading...</p>;
  if (!searchResult) return <p>No profile data</p>;

  return (
    <div>
      <Grid
        key={currentNodeIndex}
        node={searchResult.path[currentNodeIndex]}
        w={searchResult.gridWidth}
        h={searchResult.gridHeight}
      />

      <div className="flex flex-row justify-center items-center space-x-4 mt-7">
        <div className="capitalize text-lg">
          algo: <span className="uppercase">{searchResult.algo}</span>
        </div>
        <div className="capitalize text-lg">|</div>
        <div className="capitalize text-lg">score: {searchResult.score}</div>
        <div className="capitalize text-lg">|</div>
        <div className="capitalize text-lg">
          expanded nodes: {searchResult.numOfNodes}
        </div>
      </div>

      <div className="flex flex-row justify-center items-center space-x-4 my-7">
        <select
          className="py-3 px-4 pr-9 text-gray-600 bg-transparent border border-gray-200 focus:border-gray-500"
          placeholder="Algo"
          value={algo}
          onChange={(event) => setAlgo(event.target.value)}
        >
          {strategies.map((algo, i) => {
            return (
              <option value={algo} key={i} className="uppercase">
                {algo}
              </option>
            );
          })}
        </select>

        <button
          className="py-2 px-4 text-gray-600 font-semibold uppercase bg-transparent border border-gray-200 hover:border-gray-500"
          onClick={searchAndPlay}
        >
          search
        </button>

        <button className="text-4xl" onClick={previous}>
          ⬅️
        </button>
        <button className="text-4xl" onClick={play}>
          ⏯️
        </button>
        <button className="text-4xl" onClick={next}>
          ➡️
        </button>
      </div>
    </div>
  );
}
