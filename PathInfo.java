package com.manisha.assignment;

	public class PathInfo  {

		private final Gate destGate;
		private final Gate prevGate;

		public PathInfo(Gate destGate, Gate prevGate) {
		    this.destGate = destGate;
		    this.prevGate = prevGate;
		}

		public Gate getDestGate() {
		    return destGate;
		}
		
		public Gate getPrevGate() {
		    return prevGate;
		}
		
		public static PathInfo getNewPath(PathInfo path, Belt belt) {
			return(new PathInfo(belt.getDestinationForSrc(path.getDestGate()), path.getDestGate()));
		}
		}


