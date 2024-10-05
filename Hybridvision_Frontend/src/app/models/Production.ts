import {Genre} from "./Genre";

export interface Production{
  releaseDate: Date;
  id: number;
  title: string;
  bpm: number;
  duration: number;
  coverImage: string;
  audioMp3: string;
  audioWav: string;
  audioZip: string;
  genre: Genre;

}
