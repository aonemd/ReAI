"use client";

import { useState, useEffect } from "react";

const Grid = ({ node }) => {
  let ironMan = node.state.ironManPosition;
  let thanos = node.state.thanosPosition;
  let stonePositions = node.state.stonePositions;
  let warriorPositions = node.state.warriorPositions;

  return (
    <div className="">
      {[...Array(5)].map((_, i) => {
        return (
          <div className="flex flex-row justify-center items-center">
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

                return <Cell key={i} value={cellValue} />;
              })}
          </div>
        );
      })}
    </div>
  );
};
const Cell = ({ value }) => {
  return (
    <div className="flex items-center justify-center box-border h-32 w-32 border-4">
      {value}
    </div>
  );
};

export default function Home() {
  const [data, setData] = useState(null);
  const [isLoading, setLoading] = useState(true);
  const [currentNodeIndex, setCurrentNodeIndex] = useState(0);

  useEffect(() => {
    async function callAPI() {
      try {
        const res = await fetch(
          `http://localhost:8080/api/endgame/search?grid=5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3&algo=bfs`
        );
        const data = await res.json();
        console.log(data);
        setData(data);
        setLoading(false);
      } catch (err) {
        console.log(err);
      }
    }

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
    </div>
  );
}
