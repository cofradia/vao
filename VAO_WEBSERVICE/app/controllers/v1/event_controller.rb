class V1::EventController < ApplicationController

  def create
	data = params["event"]
	place_obj = Place.new(:latitude => data["place_latitude"], :longitude => data["place_longitude"], :name => data["event_place_name"])
	if place_obj.save
		event_obj = Event.new(:name => data["event_name"], :description => data["event_description"], :category_id => data["event_category"], :place_id => place_obj.id)
		if event_obj.save
			date_by_event = EventDate.new(:event_id => event_obj.id, :start_date => data["event_start_date"], :end_date => data["event_end_date"], :start_time => data["event_start_time"], :end_time => data["event_end_time"])
			if date_by_event.save
				
			end
		end
	end
  end

  def index
    update_time = params[:update_time]
    events = Event.where("created_at >= ? or update_at >= ? ", update_time)
  end

  def show
    event_id = params[:event_id]
    event = Event.find(event_id)
  end  

end
