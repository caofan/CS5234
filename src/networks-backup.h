/*
 * networks.h
 *
 *  Created on: Nov 1, 2014
 *      Author: jaemin
 */

#ifndef NETWORKS_H_
#define NETWORKS_H_

#include <algorithm>
#include <complex>
#include <vector>
#include <iostream>
#include <fstream>
#include <string>
#include <queue>
#include <climits>
#include <stdlib.h>
#define FOR(i, a, b) for(int i =(a); i <=(b); i++)
#define FORCOND(i, a, b, c) for(int i =(a); i <=(b) && (c); ++i)
#define FORDCOND(i, a, b, c) for(int i =(a); i >=(b) && (c); --i)
#define FORD(i, a, b) for(int i = (a); i >= (b); --i)
#define VAR(v, i) __typeof(i) v=(i)
#define FORE(i, c) for(VAR(i, (c).begin()); i != (c).goal(); ++i)
#define ALL(x) (x).begin(), (x).goal()
#define SZ(x) ((int)(x).size())
#define CLR(x) memset((x), 0, sizeof(x))
#define SQR(a) ((a)*(a))
#define DEBUG 1

#define MAX_nodes 2000000

using namespace std;

struct Neighbor {
	//Each node contains information of Neighbor (node and distance)
	int node;
	float distance;

	Neighbor(int _node, float _distance) :
			node(_node), distance(_distance) {
	}
	Neighbor() :
			node(0), distance(0) {

	}
};

struct Position {
	// struct for Euclidean random network
	float x, y;

	Position(float _x, float _y) :
			x(_x), y(_y) {
	}

};

void printEdges(vector<vector<Neighbor> >& edges, int n, float p) {

	char file_name[100];
	sprintf(file_name, "Edges_node%d-p%f", n, p);

	ofstream file;
	file.open(file_name);

	FOR(i,0,(int)edges.size()-1)
		FOR(j,0,(int)edges.at(i).size()-1)
			file << i << "\t" << edges.at(i).at(j).node << "\t"
					<< edges.at(i).at(j).distance << endl;
	file.close();

}

void printTrajectory(vector<int>& trajectory, int n, float p) {

	char file_name[100];
	sprintf(file_name, "Trajectory_node%d-p%f", n, p);

	ofstream file;
	file.open(file_name);

	FOR(i,0,(int)trajectory.size()-1)
		file << trajectory.at(i) << endl;
	file.close();

}
void printTrajectory_after_improvement(vector<int>& trajectory, int n,
		float p) {

	char file_name[100];
	sprintf(file_name, "Trajectory_after_improvement_node%d-p%f", n, p);

	ofstream file;
	file.open(file_name);

	FOR(i,0,(int)trajectory.size()-1)
		file << trajectory.at(i) << endl;
	file.close();

}

void printPositions(vector<Position>& position, int n, float p) {

	char file_name[100];
	sprintf(file_name, "Position_node%d-p%f", n, p);

	ofstream file;
	file.open(file_name);

	FOR(i,0,(int)position.size()-1)
		file << i << "\t" << position.at(i).x << "\t" << position.at(i).y
				<< endl;
	file.close();

}

class RealRandomNetwork {
public:

	vector<vector<Neighbor> > edges;
	vector<Position> position;

	~RealRandomNetwork() {
	}
	;
	RealRandomNetwork(int num_edges) {

		int from, to, prev_from = -1;
		float distance;

		FOR(i,1,num_edges)
		{
			cin >> from;
			cin >> to;
			cin >> distance;

			if (prev_from != from) {
				if (from != prev_from + 1) {
					//when node number increase more than 1, include dummy nodes
					FOR(t,2,from-prev_from)
					{
						vector<Neighbor> dummies;
						edges.push_back(dummies);
					}
				}
				prev_from = from;
				vector<Neighbor> dummies;
				edges.push_back(dummies);
			}
			edges.at(from).push_back(Neighbor(to, distance));
		}
		//printout for debug
//		int i = 0;
//		for (vector<vector<Neighbor> >::iterator it = edges.begin();
//				it != edges.end(); ++it) {
//			for (vector<Neighbor>::iterator it2 = (*it).begin();
//					it2 != (*it).end(); ++it2) {
//				cout << i << "\t" << (*it2).node << "\t" << (*it2).distance
//						<< endl;
//			}
//			i++;
//		}

	}

	RealRandomNetwork(int n, double D, float p) {
		FOR(i,0,n-1)
		{
			srand(time(NULL) * i);
			position.push_back(
					Position(D * ((double) rand() / RAND_MAX),
							D * ((double) rand() / RAND_MAX)));

			vector<Neighbor> dummy;
			edges.push_back(dummy);

			FOR(j, 0, i - 1)
			{
				float dist;
				if (((double) rand() / RAND_MAX) <= p) {
					dist = sqrt(
							pow(position.at(i).x - position.at(j).x, 2)
									+ pow(position.at(i).y - position.at(j).y,
											2));
					edges.at(j).push_back(Neighbor(i, dist));
					edges.at(i).push_back(Neighbor(j, dist));
				}
			}
		}
		//printout for debug
//		for (vector<vector<Neighbor> >::iterator it = edges.begin();
//				it != edges.end(); ++it) {
//			cout << "---------" << endl;
//			for (vector<Neighbor>::iterator it2 = (*it).begin();
//					it2 != (*it).end(); ++it2) {
//				cout << (*it2).node << '\t' << (*it2).distance << endl;
//			}
//		}
	}

};

struct Pair {
	int from, to;
	Pair(int _from, int _to) :
			from(_from), to(_to) {

	}
};

bool COMP(Pair a, Pair b) {
	return a.from < b.from;
}

void sort_by_from_node(int num_edge) { //function for generating sorted text file from the original data file
	//exclude dummy lines in the text file
	string dummies;
	FOR(i,1,4)
	{
		getline(cin, dummies);
	}

	int from, to;
	vector<Pair> Pairs;

	FOR (i,1,num_edge)
	{
		cin >> from;
		cin >> to;
		Pairs.push_back(Pair(from, to));
	}
	sort(Pairs.begin(), Pairs.end(), COMP);

	for (vector<Pair>::iterator it = Pairs.begin(); it != Pairs.end(); ++it)
		cout << (*it).from << "\t" << (*it).to << endl;

}

class RealRoadNetwork {
public:

	vector<vector<Neighbor> > edges;
	~RealRoadNetwork() {
	}
	;
	RealRoadNetwork(int num_edges) {

		int from, to, prev_from = -1;

		FOR(i,1,num_edges)
		{
			cin >> from;
			cin >> to;

			if (prev_from != from) {
				if (from != prev_from + 1) {
					//when node number increase more than 1, include dummy nodes
					FOR(t,2,from-prev_from)
					{
						vector<Neighbor> dummies;
						edges.push_back(dummies);
					}
				}
				prev_from = from;
				vector<Neighbor> dummies;
				edges.push_back(dummies);
			}
			edges.at(from).push_back(Neighbor(to, 1));
		}
		//printout for debug
//		int i = 0;
//		for (vector<vector<Neighbor> >::iterator it = edges.begin();
//				it != edges.end(); ++it) {
//			for (vector<Neighbor>::iterator it2 = (*it).begin();
//					it2 != (*it).end(); ++it2) {
//				cout << i << "\t" << (*it2).node << endl;
//			}
//			i++;
//		}

	}

};

bool COMP_DIST(Neighbor a, Neighbor b) {
	return a.distance < b.distance;
}

struct distance_ascending {
	bool operator()(Neighbor const& a, Neighbor const& b) const {
		return a.distance > b.distance;
	}
};

float Dijkstra(vector<vector<Neighbor> > &edges, int start_node, int end_node) {

	bool isIncluded[MAX_nodes] = { false };

	priority_queue<Neighbor, vector<Neighbor>, distance_ascending> openSet;
	openSet.push(Neighbor(start_node, 0));

	while (!openSet.empty()) {

		Neighbor min = openSet.top();
		openSet.pop();
		int current = min.node;

		if (min.node == end_node) {
			return min.distance;
		}

		for (vector<Neighbor>::iterator it = edges.at(current).begin();
				it != edges.at(current).end(); ++it) {
			if (!isIncluded[(*it).node]) {
				isIncluded[(*it).node] = true;
				double tentative_dist = min.distance + (*it).distance;
				openSet.push(Neighbor((*it).node, tentative_dist));
			}
		}
	}

	return -1;

}

float Limit_Dijkstra(float limit, vector<vector<Neighbor> > &edges,
		int start_node, int end_node) {

	bool isIncluded[MAX_nodes] = { false };

	priority_queue<Neighbor, vector<Neighbor>, distance_ascending> openSet;
	openSet.push(Neighbor(start_node, 0));

	while (!openSet.empty()) {

		Neighbor min = openSet.top();
		openSet.pop();
		int current = min.node;

		if (min.distance >= limit)
			return -1;

		if (min.node == end_node) {
			return min.distance;
		}

		for (vector<Neighbor>::iterator it = edges.at(current).begin();
				it != edges.at(current).end(); ++it) {
			if (!isIncluded[(*it).node]) {
				isIncluded[(*it).node] = true;
				double tentative_dist = min.distance + (*it).distance;
				openSet.push(Neighbor((*it).node, tentative_dist));
			}
		}
	}

	return -1;

}

class NN_algorithm {

	double path_length;
	vector<vector<Neighbor> > edges;
	double time_limit;	// for findNN_fast

public:
	vector<int> trajectory;
	vector<float> vec_len;
	~NN_algorithm() {

	}
	NN_algorithm(int start_node, vector<vector<Neighbor> > &_edges) {

		time_limit = 0.001;	//time limit of 1 millisecond for NN_fast
		edges = _edges;
		path_length = 0;
		bool visited[MAX_nodes] = { false };

		int count = 0, current = start_node;

		while (true) { // cover all nodes before going back to the original node
			visited[current] = true;
			trajectory.push_back(current);
			count++;

			Neighbor min_neighbor;
			min_neighbor = findNN_fast(current, visited);

			if (min_neighbor.node == -1) { //hit time limit. shortest path is long thus use array
				min_neighbor = findNN(current, visited);
				if (min_neighbor.node == -1) { // no neighbor found (all nodes in the entity are visited)
					//going back to original node
					float going_back_home = Dijkstra(edges, current,
							start_node);
					vec_len.push_back(going_back_home);
					path_length += going_back_home;
					return;
				}
			}

			path_length += min_neighbor.distance;
			vec_len.push_back(min_neighbor.distance);
			current = min_neighbor.node;
			//cout << current << endl;
			//cout << path_length << "\t" << count << endl;
		}

	}

	bool isAlready(vector<Neighbor>& visited_open, int node, int start,
			int end) {

		if (start > end)
			return false;

		int current = (start + end) / 2;
		if (visited_open.at(current).node > node) {		//search the head part
			return isAlready(visited_open, node, start, current - 1);
		} else if (visited_open.at(current).node < node) {//search the tail part
			return isAlready(visited_open, node, current + 1, end);
		} else
			return true;

	}

	void addTo(vector<Neighbor>& visited_open, Neighbor candi, int start,
			int end) {

		if (start > end) {
			visited_open.insert(visited_open.begin() + start, candi);
			return;
		}
		int current = (start + end) / 2;
		if (visited_open.at(current).node > candi.node) {
			addTo(visited_open, candi, start, current - 1);
		} else if (visited_open.at(current).node < candi.node) {
			addTo(visited_open, candi, current + 1, end);
		} else {
			visited_open.insert(visited_open.begin() + current, candi);
			return;
		}
	}

	Neighbor findNN_fast(int start_node, bool* visited) {
		//return node and distance from the start node

		//do not use array and perform well for finding NN when there is near one
		const clock_t start_time = clock();

		vector<Neighbor> visited_open;				//sorted by node number

		priority_queue<Neighbor, vector<Neighbor>, distance_ascending> openSet;
		openSet.push(Neighbor(start_node, 0));

		while (!openSet.empty()
				&& float(clock() - start_time) / CLOCKS_PER_SEC < time_limit) {

//			for (vector<Neighbor>::iterator it = visited_open.begin();
//					it != visited_open.end(); ++it) {
//				cout << (*it).node << "\t" << (*it).distance << endl;
//			}

			Neighbor min = openSet.top();
			openSet.pop();
			int current = min.node;

			//fix values for closed set
			if (!visited[current]) {
				return min;
			}

			for (vector<Neighbor>::iterator it = edges.at(current).begin();
					it != edges.at(current).end(); ++it) {
				if (!isAlready(visited_open, (*it).node, 0,
						(int) visited_open.size() - 1)) { //by triangle inequality, the later cannot have less distance. Thus once included, never included again
					Neighbor adjacent = Neighbor((*it).node,
							min.distance + (*it).distance);
					//by triangle inequality, once the distance is defined, it will never change
					openSet.push(adjacent);
					addTo(visited_open, adjacent, 0,
							(int) visited_open.size() - 1);
				}
			}
		}
		return Neighbor(-1, -1);

	}

	Neighbor findNN(int start_node, bool* visited) {
		//return node and distance from the start node

		bool isIncluded[MAX_nodes] = { false };

		priority_queue<Neighbor, vector<Neighbor>, distance_ascending> openSet;
		openSet.push(Neighbor(start_node, 0));

		while (!openSet.empty()) {

			Neighbor min = openSet.top();
			openSet.pop();
			int current = min.node;

			if (!visited[current]) {
				return min;
			}

			for (vector<Neighbor>::iterator it = edges.at(current).begin();
					it != edges.at(current).end(); ++it) {
				if (!isIncluded[(*it).node]) { //each node is only included once due to triangle inequality
					isIncluded[(*it).node] = true;
					double tentative_dist = min.distance + (*it).distance;
					openSet.push(Neighbor((*it).node, tentative_dist));

				}
			}
		}
		return Neighbor(-1, -1);

	}

	double length() {
		return this->path_length;
	}

}
;

float ImprovePath(vector<vector<Neighbor> > &edges, vector<int> &trajectory,
		vector<float> &len, float threshold) {
	//trajectory : a sequence of vertice.	len:vector of length (in the same order with trajectory)
	int n = trajectory.size();

//	FOR(i,0,len.size()-1)
//		cout << len.at(i) << endl;

	float d1, d2;
	float prev_len_sum;
	Neighbor min1, min2;

	FOR (i,0,n - 3)
	{
		if (len.at(i) < threshold) { //ignore short edges
			continue;
		}
		FOR(j,i+2,n-1)
		{

			if (len.at(j) < threshold || len.at(i) < threshold) //ignore short edges (len.at(i) is also evaluated in case it's updated)
				continue;

			if ((j + 1) % n == i) //when j+1 th edge equals to 0 th edge
				continue;

//			min1 = *min_element(edges.at(trajectory.at(i)).begin(),
//					edges.at(trajectory.at(i)).end(), COMP_DIST);
//			min2 = *min_element(edges.at(trajectory.at(j)).begin(),
//					edges.at(trajectory.at(j)).end(), COMP_DIST);
//
//			if (len.at(i) == min1.distance && len.at(j) == min2.distance)
//				continue;

			prev_len_sum = len.at(i) + len.at(j);

			d1 = Limit_Dijkstra(prev_len_sum, edges, trajectory.at(i),
					trajectory.at(j)); //when there is no path shorter than prev_len_sum, return -1
			if (d1 == -1)
				continue;

			d2 = Limit_Dijkstra(prev_len_sum - d1, edges, trajectory.at(i + 1),
					trajectory.at((j + 1) % n)); //when there is no path shorter than prev_len_sum-d1, return -1
			if (d2 == -1)
				continue;

//			cout << "(" << point.at(trajectory.at(i)).x << ","
//					<< point.at(trajectory.at(i)).y << ")" << "-("
//					<< point.at(trajectory.at(i + 1)).x << ","
//					<< point.at(trajectory.at(i + 1)).y << ") : " << len.at(i)
//					<< "\t" << "(" << point.at(trajectory.at(j)).x << ","
//					<< point.at(trajectory.at(j)).y << ")" << "-("
//					<< point.at(trajectory.at((j + 1) % n)).x << ","
//					<< point.at(trajectory.at((j + 1) % n)).y << ") : "
//					<< len.at(j) << endl << "d1 : " << d1 << "d2 : " << d2
//					<< endl;
//			cout << "improvement" << "\t" << prev_len_sum - d1 - d2 << endl;

			//reconstruct trajectory
			vector<int> new_trajectory;
			FOR(k,0,i) //from 0 to i
				new_trajectory.push_back(trajectory.at(k));
			FORD(k,j,i+1) //from j back to i+1
				new_trajectory.push_back(trajectory.at(k));
			FOR(k,j+1,n-1) //after j+1 up to the end
				new_trajectory.push_back(trajectory.at(k));
			trajectory = new_trajectory;

			//reset trajectory and length
			vector<float> new_len;
			FOR(k,0,i-1) //from 0 to i
				new_len.push_back(len.at(k));
			FORD(k,j,i) //from j back to i+1
				new_len.push_back(len.at(k));
			FOR(k,j+1,n-1) //after j+1 up to the end
				new_len.push_back(len.at(k));
			len = new_len;

			len.at(i) = d1;
			len.at(j) = d2;

//			FOR(i,0,len.size()-1)
//				cout << trajectory.at(i) << endl;

		}
	}
	//sum up all lengths
	float result = 0;
	for (vector<float>::iterator it = len.begin(); it != len.end(); ++it)
		result += (*it);

	return result;
}

#endif /* NETWORKS_H_ */
