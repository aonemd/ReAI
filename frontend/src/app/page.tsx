"use client";

import { useState, useEffect } from "react";

const Grid = ({ node }) => {
  let ironMan = node.state.ironManPosition;
  let thanos = node.state.thanosPosition;
  let stonePositions = node.state.stonePositions;
  let warriorPositions = node.state.warriorPositions;
  let operator = node.operator;

  return (
    <div className="">
      <div className="flex flex-row justify-center items-center my-2">
        <span className="font-semibold">{operator.name}</span>
      </div>

      {[...Array(5)].map((_, i) => {
        return (
          <div className="flex flex-row justify-center items-center" key={i}>
            {Array(5)
              .fill("-")
              .map((_, j) => {
                let cellValue;
                let curCell = { x: i, y: j };

                if (
                  stonePositions.some(
                    (pos) => pos.x == curCell.x && pos.y == curCell.y
                  )
                )
                  cellValue = "S";
                if (
                  warriorPositions.some(
                    (pos) => pos.x == curCell.x && pos.y == curCell.y
                  )
                )
                  cellValue = "W";
                if (thanos.x == curCell.x && thanos.y == curCell.y)
                  cellValue = "T";
                if (ironMan.x == curCell.x && ironMan.y == curCell.y)
                  cellValue = "I";

                return <Cell key={`${i}${j}`} value={cellValue} />;
              })}
          </div>
        );
      })}
    </div>
  );
};
const Cell = ({ value }) => {
  return (
    <div className="flex items-center justify-center box-border h-32 w-32 border-4 text-6xl">
      {value}
    </div>
  );
};

export default function Home() {
  const [strategies, setStrategies] = useState(null);
  const [algo, setAlgo] = useState("bfs");
  const [data, setData] = useState(null);
  const [isLoading, setLoading] = useState(true);
  const [currentNodeIndex, setCurrentNodeIndex] = useState(0);

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

  async function callAPI() {
    try {
      const res = await fetch(
        `http://localhost:8080/api/endgame/search?grid=5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3&algo=${algo}`
      );
      const data = await res.json();
      console.log(data);
      setData(data);
      setCurrentNodeIndex(0);
      setLoading(false);
    } catch (err) {
      console.log(err);
    }
  }

  useEffect(() => {
    getAlgos();
    callAPI();
  }, []);

  if (isLoading) return <p>Loading...</p>;
  if (!data) return <p>No profile data</p>;

  if (currentNodeIndex < data.path.length - 1) {
    setTimeout(() => {
      console.log("Index updated");
      setCurrentNodeIndex(currentNodeIndex + 1);
    }, 300);
  }

  return (
    <div>
      <h1>Hell, world!</h1>

      <Grid key={currentNodeIndex} node={data.path[currentNodeIndex]} />

      <div className="flex flex-row justify-center items-center space-x-4 mt-7">
        <div className="capitalize text-lg">
          algo: <span className="uppercase">{data.algo}</span>
        </div>
        <div className="capitalize text-lg">|</div>
        <div className="capitalize text-lg">score: {data.score}</div>
        <div className="capitalize text-lg">|</div>
        <div className="capitalize text-lg">
          expanded nodes: {data.numOfNodes}
        </div>
      </div>

      <div className="flex flex-row justify-center items-center space-x-4 mt-7">
        <select
          className="py-3 px-4 pr-9 bg-transparent border-gray-200 text-sm focus:border-blue-500 focus:ring-blue-500 dark:bg-slate-900 dark:border-gray-700 dark:text-gray-400"
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
          className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent"
          onClick={callAPI}
        >
          search
        </button>
      </div>
    </div>
  );
}
